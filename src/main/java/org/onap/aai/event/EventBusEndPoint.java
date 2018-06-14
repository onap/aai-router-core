/**
 * ============LICENSE_START=======================================================
 * org.onap.aai
 * ================================================================================
 * Copyright © 2018 AT&T Intellectual Property. All rights reserved.
 * Copyright © 2018 Amdocs
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

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.onap.aai.cl.api.Logger;
import org.onap.aai.cl.eelf.LoggerFactory;
import org.onap.aai.event.api.EventConsumer;
import org.onap.aai.event.api.EventPublisher;

@UriEndpoint(scheme = "event-bus", syntax = "event-bus:name",
consumerClass = EventBusConsumer.class, title = "event-bus")
public class EventBusEndPoint extends AbstractEventBusEndpoint {
	@UriParam(label = "eventTopic")
	@Metadata(required = "true")
	private String eventTopic;
	@UriParam(label = "poolSize")
	@Metadata(required = "true", defaultValue="20")
	private int poolSize = 20;
	@UriParam(label = "pollingDelay")
	@Metadata(required = "true", defaultValue="30000")
	private int pollingDelay = 30000;
 
	EventConsumer consumer; //This would be injected via bean through camel route when passed with #
	
	EventPublisher publisher; //This would be injected via bean through camel route when passed with #
	
	private Logger logger = LoggerFactory.getInstance().getLogger(EventBusEndPoint.class);
	
	public EventBusEndPoint(String uri, EventBusComponent component) {
		super(uri, component);
	}
	 
	@Override
	public Producer createProducer() throws Exception {
		return new EventBusProducer(this, publisher);
	}

	@Override
	public Consumer createConsumer(Processor processor) throws Exception {
		return new EventBusConsumer(this, processor, consumer);
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
	
	void close() throws Exception {
	   if(consumer != null)
		   consumer.close();
		if(publisher != null)
		   publisher.close();
	}	
	
	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}

	public void setPollingDelay(int pollingDelay) {
		this.pollingDelay = pollingDelay;
	}

	public int getPollingDelay() {
    	return pollingDelay;
    }
	public int getPoolSize() {
		return poolSize;
	}
	public String getEventTopic() {
		return eventTopic;
	}

	public void setEventTopic(String eventTopic) {
		this.eventTopic = eventTopic;
	}

	public void setConsumer(EventConsumer consumer) {
		this.consumer = consumer;
	}

	public void setPublisher(EventPublisher publisher) {
		this.publisher = publisher;
	}
}
