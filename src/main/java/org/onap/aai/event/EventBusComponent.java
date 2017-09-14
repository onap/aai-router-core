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
 *
 * ECOMP is a trademark and service mark of AT&T Intellectual Property.
 */
package org.onap.aai.event;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.UriEndpointComponent;

import java.util.Map;

/**
 * Represents the component that manages {@link EventBusEndpoint}.
 */
public class EventBusComponent extends UriEndpointComponent {

  public EventBusComponent() {
    super(EventBusEndpoint.class);
  }

  public EventBusComponent(CamelContext context) {
    super(context, EventBusEndpoint.class);
  }

  protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters)
      throws Exception {
    Endpoint endpoint = new EventBusEndpoint(uri, this);
    setProperties(endpoint, parameters);
    return endpoint;
  }
}
