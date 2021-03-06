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

import org.apache.camel.Category;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.support.DefaultEndpoint;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriPath;

import java.util.Map;


@UriEndpoint(scheme = "ecomp-rest", syntax = "ecomp-rest:op",
    consumerClass = RestClientConsumer.class, label = "RestClient2", title = "ecomp-rest",
    firstVersion = "1.0.0", category = {Category.CORE})
public class RestClientEndpoint extends DefaultEndpoint {

  public static final String CONTEXT_PARAM_CLIENT_CERT = "ecomp-client-cert";
  public static final String CONTEXT_PARAM_KEYSTORE = "ecomp-keystore";
  public static final String CONTEXT_PARAM_KEYSTORE_PWD = "ecomp-keystore-password";

  public static final String IN_HEADER_URL = "ecomp-url";

  public static final String OUT_HEADER_RESPONSE_CODE = "ecomp-response-code";
  public static final String OUT_HEADER_RESPONSE_MSG = "ecomp-response-message";

  @UriPath(description = "op")
  @Metadata(required = true)
  private String op;
  @UriParam(description = "ecomp client cert")
  private String ecompClientCert;
  @UriParam(description = "ecomp key store")
  private String ecompKeystore;
  @UriParam(description = "ecomp key store passwd")
  private String ecompKeystorePassword;

  public RestClientEndpoint() {}

  public RestClientEndpoint(String uri, RestClientComponent component) {
    super(uri, component);
  }

  @Override
  public void setProperties(Object bean, Map<String, Object> parameters) {
    super.setProperties(bean, parameters);
  }

  public Producer createProducer() throws Exception {
    return new RestClientProducer(this);
  }
  @Override
  public Consumer createConsumer(Processor processor) throws Exception {
    return new RestClientConsumer(this, processor);
  }
  @Override
  public boolean isSingleton() {
    return true;
  }

  public String getOp() {
    return op;
  }

  public void setOp(String op) {
    this.op = op;
  }

  public String getEcompClientCert() {
    return ecompClientCert;
  }

  public void setEcompClientCert(String ecompClientCert) {
    this.ecompClientCert = ecompClientCert;
  }

  public String getEcompKeystore() {
    return ecompKeystore;
  }

  public void setEcompKeystore(String ecompKeystore) {
    this.ecompKeystore = ecompKeystore;
  }

  public String getEcompKeystorePassword() {
    return ecompKeystorePassword;
  }

  public void setEcompKeystorePassword(String ecompKeystorePassword) {
    this.ecompKeystorePassword = ecompKeystorePassword;
  }
}
