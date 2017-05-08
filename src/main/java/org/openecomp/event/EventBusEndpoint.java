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
package org.openecomp.event;


import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriPath;
import org.eclipse.jetty.util.security.Password;

/**
 * Represents a EventBus endpoint.
 */
@UriEndpoint(scheme = "event-bus", syntax = "event-bus:name",
    consumerClass = EventBusConsumer.class, title = "event-bus")
public class EventBusEndpoint extends DefaultEndpoint {
  @UriPath
  @Metadata(required = "true")
  private String name;

  @UriParam(label = "eventTopic")
  @Metadata(required = "true")
  private String eventTopic;
  @UriParam(label = "groupName")
  @Metadata(required = "true")
  private String groupName;
  @UriParam(label = "groupId")
  @Metadata(required = "true")
  private String groupId;
  @UriParam(label = "apiKey")
  private String apiKey;
  @UriParam(label = "apiSecret")
  private String apiSecret;
  @UriParam(label = "url")
  @Metadata(required = "true")
  private String url;
  @UriParam(label = "poolSize")
  @Metadata(required = "true", defaultValue="20")
  private int poolSize = 20;
  @UriParam(label = "pollingDelay")
  @Metadata(required = "true", defaultValue="30000")
  private int pollingDelay = 30000;
  
  public EventBusEndpoint() {}

  public EventBusEndpoint(String uri, EventBusComponent component) {
    super(uri, component);
  }

  public EventBusEndpoint(String endpointUri) {
    super(endpointUri);
  }

  public Producer createProducer() throws Exception {
    return new EventBusProducer(this);
  }

  public Consumer createConsumer(Processor processor) throws Exception {
    return new EventBusConsumer(this, processor);
  }

  public boolean isSingleton() {
    return false;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getEventTopic() {
    return eventTopic;
  }

  public void setEventTopic(String eventTopic) {
    this.eventTopic = eventTopic;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getApiKey() {
    return apiKey == null ? null : Password.deobfuscate(apiKey);
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getApiSecret() {
    return apiSecret == null ? null : Password.deobfuscate(apiSecret);
  }

  public void setApiSecret(String apiSecret) {
    this.apiSecret = apiSecret;
  }
  
  public int getPoolSize() {
	return poolSize;
  }

  public void setPoolSize(int poolsize) {
	this.poolSize = poolsize;
  }

  public int getPollingDelay() {
	  return pollingDelay;
  }

  public void setPollingDelay(int pollingDelay) {
	  this.pollingDelay = pollingDelay;
  }

  public String getUrl() {
	  return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}

