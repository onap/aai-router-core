/**
 * ============LICENSE_START=======================================================
 * org.onap.aai
 * ================================================================================
 * Copyright © 2017 AT&T Intellectual Property. All rights reserved.
 * Copyright © 2017 Amdocs
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
package org.onap.aai.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;

import org.apache.camel.Endpoint;
import org.junit.Before;
import org.junit.Test;

public class EventBusTest {

    /**
     * Test case initialization
     * 
     * @throws Exception the exception
     */
    @Before
    public void init() throws Exception {
    }

    @Test
    public void validateProducer() throws Exception {
        try {
            DMaaPEventBusComponent rc = new DMaaPEventBusComponent();
            DMaaPEventBusEndpoint endpoint = new DMaaPEventBusEndpoint("http://host.com:8443/endpoint", rc);
            endpoint.setPassword("OBF:1y0q1uvc1uum1uvg1pil1pjl1uuq1uvk1uuu1y10");
            endpoint.setUsername("OBF:1y0q1uvc1uum1uvg1pil1pjl1uuq1uvk1uuu1y10");
            endpoint.setEventTopic("eventTopic");
            endpoint.setConsumerId("groupId");
            endpoint.setConsumerGroup("gn");
            endpoint.setName("name");
            endpoint.setPoolSize(45);
            endpoint.setPollingDelay(10);
            endpoint.setUrl("url");

            assertTrue(endpoint.getPassword().compareTo("onapSecret") == 0);
            assertTrue(endpoint.getUsername().compareTo("onapSecret") == 0);
            assertTrue(endpoint.getEventTopic().compareTo("eventTopic") == 0);
            assertTrue(endpoint.getConsumerId().compareTo("groupId") == 0);
            assertTrue(endpoint.getConsumerGroup().compareTo("gn") == 0);
            assertTrue(endpoint.getName().compareTo("name") == 0);
            assertTrue(endpoint.getPoolSize() == 45);
            assertTrue(endpoint.getPollingDelay() == 10);
            assertTrue(endpoint.getUrl().compareTo("url") == 0);
            assertFalse(endpoint.isSingleton());

            EventBusProducer producer = (EventBusProducer)endpoint.createProducer();
            assertTrue(producer.getEndpoint() != null);
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
    
    @Test
    public void validateEventBusComponent() throws Exception {
        DMaaPEventBusComponent rc = new DMaaPEventBusComponent(new TestCamelContext());
        Endpoint endpoint = rc.createEndpoint("http://host.com:8443/endpoint", null, new HashMap<String, Object>());
        assertTrue(endpoint.getEndpointUri().equals("http://host.com:8443/endpoint"));
    }
    
    @Test
    public void validateConsumer() throws Exception {
        try {
            DMaaPEventBusComponent rc = new DMaaPEventBusComponent();
            DMaaPEventBusEndpoint endpoint = new DMaaPEventBusEndpoint("http://host.com:8443/endpoint", rc);

            endpoint.setPassword("OBF:1y0q1uvc1uum1uvg1pil1pjl1uuq1uvk1uuu1y10");
            endpoint.setUsername("OBF:1y0q1uvc1uum1uvg1pil1pjl1uuq1uvk1uuu1y10");
            endpoint.setEventTopic("eventTopic");
            endpoint.setConsumerId("groupId");
            endpoint.setConsumerGroup("gn");
            endpoint.setName("name");
            endpoint.setPoolSize(45);
            endpoint.setPollingDelay(10);
            endpoint.setUrl("url");

            TestProcessor processor = new TestProcessor();
            EventBusConsumer consumer = (EventBusConsumer)endpoint.createConsumer(processor);

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
