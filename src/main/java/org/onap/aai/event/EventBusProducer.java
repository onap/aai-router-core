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
package org.onap.aai.event;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultProducer;
import org.onap.aai.event.api.EventPublisher;

/**
 * The EventBus producer.
 */
public class EventBusProducer extends DefaultProducer {
  private AbstractEventBusEndpoint endpoint;
  
  private EventPublisher publisher;

  public EventBusProducer(AbstractEventBusEndpoint endpoint, EventPublisher publisher) {
	    super(endpoint);
	    this.endpoint = endpoint;
	    this.publisher = publisher;
  }
  
  @Override
  public void process(Exchange exchange) throws Exception {
    // Publishing to event bus is currently not supported
  }

}
