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

import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.support.DefaultMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.onap.aai.logging.RouterCoreMsgs;
import org.onap.aai.restclient.client.Headers;

@RunWith(MockitoJUnitRunner.class)
public class RestClientTest {


    @Mock
    public ExtendedCamelContext context;

    /**
     * Test case initialization
     * 
     * @throws Exception the exception
     */
    @Before
    public void init() throws Exception {
    }

    @Test
    public void validate() throws Exception {

        try {
            RestClientComponent rc = new RestClientComponent(context);
            RestClientEndpoint endpoint = new RestClientEndpoint("http://host.com:8443/endpoint", rc);

            endpoint.setEcompClientCert("client-cert");
            endpoint.setEcompKeystore("keystore");
            endpoint.setEcompKeystorePassword("OBF:1y0q1uvc1uum1uvg1pil1pjl1uuq1uvk1uuu1y10");
            endpoint.setOp("GET");


            assertTrue(endpoint.getEcompClientCert().compareTo("client-cert") == 0);
            assertTrue(endpoint.getEcompKeystore().compareTo("keystore") == 0);
            assertTrue(endpoint.getEcompKeystorePassword().compareTo("OBF:1y0q1uvc1uum1uvg1pil1pjl1uuq1uvk1uuu1y10") == 0);
            assertTrue(endpoint.getOp().compareTo("GET") == 0);
            assertTrue(endpoint.isSingleton());

            RestClientProducer producer = (RestClientProducer)endpoint.createProducer();
            assertTrue(producer != null);

            Method method = RestClientProducer.class.getDeclaredMethod("populateRestHeaders", Exchange.class);
            method.setAccessible(true);

            Exchange exchange = endpoint.createExchange();
            exchange.setProperty(Exchange.TO_ENDPOINT, "mock://get");
            DefaultMessage in = (DefaultMessage)exchange.getIn();
            
            in.setHeader(RestClientEndpoint.IN_HEADER_URL, "svc/endpoint");
            in.setHeader(Headers.FROM_APP_ID, "val1");
            in.setHeader(Headers.TRANSACTION_ID, "val2");
            in.setHeader(Headers.RESOURCE_VERSION, "val2");
            in.setHeader(Headers.ETAG, "val2");
            in.setHeader(Headers.IF_MATCH, "val2");
            in.setHeader(Headers.IF_NONE_MATCH, "val2");
            in.setHeader(Headers.ACCEPT, "val2");
            in.setHeader("Content-Type", "val2");

            Map<String, List<String>> headers = (Map<String, List<String>>)method.invoke(producer, exchange); 
            assertTrue(headers.size() == 8);  

            producer.process(exchange);

            System.out.println(RouterCoreMsgs.EVENT_CONSUMER_CREATION_EXCEPTION);
        }
        catch (Exception ex) {
            StringWriter writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter( writer );
            ex.printStackTrace( printWriter );
            printWriter.flush();
            System.out.println(writer.toString());
            throw ex;
        }
    }

}
