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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.Processor;
import org.apache.camel.support.DefaultMessage;
import org.apache.camel.support.MessageSupport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.onap.aai.event.api.EventConsumer;
import org.onap.aai.event.api.EventPublisher;

@RunWith(MockitoJUnitRunner.class)
public class EventBusTest {
	@Mock
    public EventConsumer consumer;
	
	@Mock
    public EventPublisher publisher;
	
	@Mock
	public ExtendedCamelContext context;
	
	@Mock
	public Processor processor;

	@Mock
	Exchange exchange;
	
	@Mock
	AbstractEventBusEndpoint endPoint;
	
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
    	EventBusComponent rc = new EventBusComponent();
        EventBusEndPoint endpoint = new EventBusEndPoint("http://host.com:8443/endpoint", rc);
        endpoint.setEventTopic("eventTopic");
        endpoint.setPublisher(publisher);
        endpoint.setPoolSize(45);
        endpoint.setPollingDelay(10);
        
        assertTrue(endpoint.getEventTopic().compareTo("eventTopic") == 0);
        assertTrue(endpoint.getPoolSize() == 45);
        assertTrue(endpoint.getPollingDelay() == 10);
        assertFalse(endpoint.isSingleton());
        EventBusProducer producer = (EventBusProducer)endpoint.createProducer();
        assertTrue(producer.getEndpoint() != null);
        endpoint.end();
    }
    
    @Test
    public void validateEventBusComponent() throws Exception {
        EventBusComponent rc = new EventBusComponent(context);
        Endpoint endpoint = rc.createEndpoint("http://host.com:8443/endpoint", null, new HashMap<String, Object>());
        assertTrue(endpoint.getEndpointUri().equals("http://host.com:8443/endpoint"));
    }
    
    @Test
    public void validateConsumer() throws Exception {
        EventBusComponent rc = new EventBusComponent();
        EventBusEndPoint endpoint = new EventBusEndPoint("http://host.com:8443/endpoint", rc);
        
        endpoint.setConsumer(consumer);
        endpoint.setEventTopic("eventTopic");
        endpoint.setPoolSize(45);
        endpoint.setPollingDelay(10);
        
        assertTrue(endpoint.getEventTopic().compareTo("eventTopic") == 0);
        assertTrue(endpoint.getPoolSize() == 45);
        assertTrue(endpoint.getPollingDelay() == 10);
        assertFalse(endpoint.isSingleton());
        
        EventBusConsumer consumer = (EventBusConsumer)endpoint.createConsumer(processor);
    }
    
    @Test
    public void validateConsumerPoll() throws Exception {
    	MessageSupport me = new DefaultMessage(context);
    	List<String> list = new ArrayList<>();
    	list.add("Message 1");
    	list.add("Message 2");
    	
    	Mockito.when(consumer.consumeAndCommit()).thenReturn(list);
        Mockito.when(endPoint.createExchange()).thenReturn(exchange);
        Mockito.when(exchange.getIn()).thenReturn(me);
        Mockito.when(exchange.getOut()).thenReturn(me);
        
        EventBusConsumer busConsumer = new EventBusConsumer(endPoint, processor, consumer);
        int messages = busConsumer.poll();
    }
}
