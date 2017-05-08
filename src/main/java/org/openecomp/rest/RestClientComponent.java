/**
 * ============LICENSE_START=======================================================
 * DataRouter
 * ================================================================================
 * Copyright © 2017 AT&T Intellectual Property.
 * Copyright © 2017 Amdocs
 * All rights reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License ati
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 *
 * ECOMP and OpenECOMP are trademarks
 * and service marks of AT&T Intellectual Property.
 */
package org.openecomp.rest;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.UriEndpointComponent;

import java.util.Map;


/**
 * Represents the component that manages {@link RestClientEndpoint}.
 */
public class RestClientComponent extends UriEndpointComponent {

  public RestClientComponent() {
    super(RestClientEndpoint.class);
  }

  public RestClientComponent(CamelContext context) {
    super(context, RestClientEndpoint.class);
  }

  @Override
  protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters)
      throws Exception {

    Endpoint endpoint = new RestClientEndpoint(uri, this);
    setProperties(endpoint, parameters);
    return endpoint;
  }
}
