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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.PrintWriter;
import java.io.StringWriter;

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
  public void validate() throws Exception {

    try {
      EventBusComponent rc = new EventBusComponent();
      EventBusEndpoint endpoint = new EventBusEndpoint("http://host.com:8443/endpoint", rc);

      endpoint.setApiSecret("OBF:1y0q1uvc1uum1uvg1pil1pjl1uuq1uvk1uuu1y10");
      endpoint.setApiKey("OBF:1y0q1uvc1uum1uvg1pil1pjl1uuq1uvk1uuu1y10");
      endpoint.setEventTopic("eventTopic");
      endpoint.setGroupId("groupId");
      endpoint.setGroupName("gn");
      endpoint.setName("name");
      endpoint.setPoolSize(45);
      endpoint.setPollingDelay(10);
      endpoint.setUrl("url");

      assertTrue(endpoint.getApiSecret().compareTo("onapSecret") == 0);
      assertTrue(endpoint.getApiKey().compareTo("onapSecret") == 0);
      assertTrue(endpoint.getEventTopic().compareTo("eventTopic") == 0);
      assertTrue(endpoint.getGroupId().compareTo("groupId") == 0);
      assertTrue(endpoint.getGroupName().compareTo("gn") == 0);
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
    
}
