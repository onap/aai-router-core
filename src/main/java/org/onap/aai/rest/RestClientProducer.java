/**
 * ============LICENSE_START=======================================================
 * org.onap.aai
 * ================================================================================
 * Copyright © 2017-2018 AT&T Intellectual Property. All rights reserved.
 * Copyright © 2017-2018 Amdocs
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */
package org.onap.aai.rest;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.eclipse.jetty.util.security.Password;
import org.onap.aai.event.EventBusConsumer;
import org.onap.aai.restclient.client.Headers;
import org.onap.aai.restclient.client.OperationResult;
import org.onap.aai.restclient.client.RestClient;
import org.onap.aai.restclient.enums.RestAuthenticationMode;
import org.onap.aai.restclient.rest.HttpUtil;
import org.onap.aai.cl.api.Logger;
import org.onap.aai.cl.eelf.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * The EcompRest producer.
 */
public class RestClientProducer extends DefaultProducer {

  private enum Operation {
    GET, PUT, POST, DELETE
  }

  private RestClientEndpoint endpoint;

  /** REST client used for sending HTTP requests. */
  private RestClient restClient;

  private Logger logger = LoggerFactory.getInstance().getLogger(RestClientProducer.class);


  public RestClientProducer(RestClientEndpoint endpoint) {
    super(endpoint);
    this.endpoint = endpoint;
  }

  @Override
  public void process(Exchange exchange) {

    // Extract the URL for our REST request from the IN message header.
    String url = exchange.getIn().getHeader(RestClientEndpoint.IN_HEADER_URL).toString();

    // Populate the HTTP Request header values from any values passed in via the
    // IN message headers.
    Map<String, List<String>> headers = populateRestHeaders(exchange);

    if (logger.isDebugEnabled()) {
      StringBuilder sb = new StringBuilder();
      sb.append("Process REST request - operation=").append(getOperation(exchange));
      sb.append(" headers=[");
      for (String key : headers.keySet()) {
        sb.append("{").append(key).append("->").append(headers.get(key)).append("} ");
      }
      sb.append("]");
      sb.append(" content: ").append(exchange.getIn().getBody());
      logger.debug(sb.toString());
    }

    // Now, invoke the REST client to perform the operation.
    OperationResult result;
    switch (getOperation(exchange)) {

      case GET:
        result = getRestClient().get(url, headers, MediaType.APPLICATION_JSON_TYPE);
        break;

      case PUT:
        result = getRestClient().put(url, exchange.getIn().getBody().toString(), headers,
            MediaType.APPLICATION_JSON_TYPE, null);
        break;

      case POST:
        result = getRestClient().post(url, exchange.getIn().getBody().toString(), headers,
            MediaType.APPLICATION_JSON_TYPE, MediaType.APPLICATION_JSON_TYPE);
        break;

      case DELETE:
        result = getRestClient().delete(url, headers, MediaType.APPLICATION_JSON_TYPE);
        break;

      default:
        // The supplied operation is not supported.
        result = new OperationResult();
        result.setResultCode(Response.Status.BAD_REQUEST.getStatusCode());
        result.setFailureCause("Unsupported HTTP Operation: " + getOperation(exchange));

        break;
    }

    /** Just use IN headers as camel does not pass incoming headers from IN to OUT so they might be lost .
    Reference : http://camel.apache.org/using-getin-or-getout-methods-on-exchange.html **/
    exchange.getIn().setHeader(RestClientEndpoint.OUT_HEADER_RESPONSE_CODE,
        result.getResultCode());
    if (HttpUtil.isHttpResponseClassSuccess(result.getResultCode())) {
      exchange.getIn().setHeader(RestClientEndpoint.OUT_HEADER_RESPONSE_MSG,
          responseStatusStringFromResult(result));
      exchange.getIn().setBody(result.getResult());
    } else {
      exchange.getIn().setHeader(RestClientEndpoint.OUT_HEADER_RESPONSE_MSG,
          result.getFailureCause());
    }

  }


  /**
   * Extracts the requested REST operation from the exchange message.
   * 
   * @param exchange - The Camel exchange to pull the operation from.
   * 
   * @return - The REST operation being requested.
   */
  private Operation getOperation(Exchange exchange) {

    String toEndpoint = ((String) exchange.getProperty(Exchange.TO_ENDPOINT));

    String operation = toEndpoint.substring((toEndpoint.lastIndexOf("://") + 3));

    int position = operation.indexOf('?');
    if (position >= 0) {
      operation = operation.substring(0, position);
    }

    return Operation.valueOf(operation.toUpperCase());
  }



  /**
   * This method extracts values from the IN message which are intended to be used to populate the
   * HTTP Header entries for our REST request.
   * 
   * @param exchange - The Camel exchange to extract the HTTP header parameters from.
   * 
   * @return - A map of HTTP header names and values.
   */
  private Map<String, List<String>> populateRestHeaders(Exchange exchange) {

    Map<String, List<String>> headers = new HashMap<>();

    if (exchange.getIn().getHeader(Headers.FROM_APP_ID) != null) {
      headers.put(Headers.FROM_APP_ID,
          Arrays.asList(exchange.getIn().getHeader(Headers.FROM_APP_ID).toString()));
    }
    if (exchange.getIn().getHeader(Headers.TRANSACTION_ID) != null) {
      headers.put(Headers.TRANSACTION_ID,
          Arrays.asList(exchange.getIn().getHeader(Headers.TRANSACTION_ID).toString()));
    }
    if (exchange.getIn().getHeader(Headers.RESOURCE_VERSION) != null) {
      headers.put(Headers.RESOURCE_VERSION,
          Arrays.asList(exchange.getIn().getHeader(Headers.RESOURCE_VERSION).toString()));
    }
    if (exchange.getIn().getHeader(Headers.ETAG) != null) {
      headers.put(Headers.ETAG, Arrays.asList(exchange.getIn().getHeader(Headers.ETAG).toString()));
    }
    if (exchange.getIn().getHeader(Headers.IF_MATCH) != null) {
      headers.put(Headers.IF_MATCH,
          Arrays.asList(exchange.getIn().getHeader(Headers.IF_MATCH).toString()));
    }
    if (exchange.getIn().getHeader(Headers.IF_NONE_MATCH) != null) {
      headers.put(Headers.IF_NONE_MATCH,
          Arrays.asList(exchange.getIn().getHeader(Headers.IF_NONE_MATCH).toString()));
    }
    if (exchange.getIn().getHeader(Headers.ACCEPT) != null) {
      headers.put(Headers.ACCEPT,
          Arrays.asList(exchange.getIn().getHeader(Headers.ACCEPT).toString()));
    }
    if (exchange.getIn().getHeader("Content-Type") != null) {
      headers.put("Content-Type",
          Arrays.asList(exchange.getIn().getHeader("Content-Type").toString()));
    }

    return headers;
  }


  /**
   * This helper method converts an HTTP response code into the associated string.
   * 
   * @param result - A result object to get the response code from.
   * 
   * @return - The string message associated with the supplied response code.
   */
  private String responseStatusStringFromResult(OperationResult result) {

    // Not every valid response code is actually represented by the Response.Status
    // object, so we need to guard against missing codes, otherwise we throw null
    // pointer exceptions when we try to generate our metrics logs...
    Response.Status responseStatus = Response.Status.fromStatusCode(result.getResultCode());
    String responseStatusCodeString = "";
    if (responseStatus != null) {
      responseStatusCodeString = responseStatus.toString();
    }

    return responseStatusCodeString;
  }

  /**
   * Instantiate the REST client that will be used for sending our HTTP requests.
   * 
   * @return - An instance of the REST client.
   */
  private RestClient getRestClient() {

    if (restClient == null) {
      
      String keystorePassword = endpoint.getEcompKeystorePassword();
      String clientCertFilename = endpoint.getEcompClientCert();

      if (logger.isDebugEnabled()) {
        logger.debug("Instantiating REST Client with client_cert=" + clientCertFilename
            + " keystorePassword=" + keystorePassword);
      }

      String deobfuscatedCertPassword = keystorePassword.startsWith("OBF:")?Password.deobfuscate(keystorePassword):keystorePassword;
      
      // Create REST client for search service
      restClient = new RestClient().authenticationMode(RestAuthenticationMode.SSL_CERT).validateServerHostname(false)
              .validateServerCertChain(false).clientCertFile(clientCertFilename)
              .clientCertPassword(deobfuscatedCertPassword);
    }

    return restClient;
  }
}
