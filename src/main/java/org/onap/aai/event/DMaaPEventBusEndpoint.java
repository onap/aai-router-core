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


import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriPath;
import org.eclipse.jetty.util.security.Password;
import org.onap.aai.event.client.DMaaPEventConsumer;

/**
 * Represents a EventBus endpoint.
 */
@UriEndpoint(scheme = "dmaap-event-bus", syntax = "dmaap-event-bus:name",
    consumerClass = EventBusConsumer.class, title = "dmaap-event-bus")
public class DMaaPEventBusEndpoint extends AbstractEventBusEndpoint {
  @UriPath
  @Metadata(required = "true")
  private String name;

  @UriParam(label = "eventTopic")
  @Metadata(required = "true")
  private String eventTopic;
  @UriParam(label = "consumerGroup")
  @Metadata(required = "true")
  private String consumerGroup;
  @UriParam(label = "consumerId")
  @Metadata(required = "true")
  private String consumerId;
  @UriParam(label = "username")
  private String username;
  @UriParam(label = "password")
  private String password;
  @UriParam(label = "url")
  @Metadata(required = "true")
  private String url;
  @UriParam(label = "poolSize")
  @Metadata(required = "true", defaultValue="20")
  private int poolSize = 20;
  @UriParam(label = "pollingDelay")
  @Metadata(required = "true", defaultValue="30000")
  private int pollingDelay = 30000;
  @UriParam(label = "transportType")
  @Metadata(required = "true", defaultValue="HTTPAUTH")
  private String transportType = "HTTPAUTH";

  private DMaaPEventConsumer dmaapConsumer;
  
  public DMaaPEventBusEndpoint() {}

  public DMaaPEventBusEndpoint(String uri, DMaaPEventBusComponent component) {
    super(uri, component);
  }

  public DMaaPEventBusEndpoint(String endpointUri) {
    super(endpointUri);
  }

  @Override
  void close() {
    // Don't have to do anything for DMaaP
  }

  @Override
  public Producer createProducer() throws Exception {
    return new EventBusProducer(this);
  }
  @Override
  public Consumer createConsumer(Processor processor) throws Exception {
    // TODO: other overloads based on filled-in properties
    dmaapConsumer = new DMaaPEventConsumer(url, eventTopic, username, Password.deobfuscate(password), consumerGroup, consumerId, 15000, 1000, transportType);
    return new EventBusConsumer(this, processor, dmaapConsumer);
  }
  @Override
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

  public String getConsumerGroup() {
    return consumerGroup;
  }

  public void setConsumerGroup(String consumerGroup) {
    this.consumerGroup = consumerGroup;
  }

  public String getConsumerId() {
    return consumerId;
  }

  public void setConsumerId(String consumerId) {
    this.consumerId = consumerId;
  }

  public String getUsername() {
    return username == null ? null : Password.deobfuscate(username);
    //return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password == null ? null : Password.deobfuscate(password);
    //return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  public String getTransportType() {
    return transportType;
  }

  public void setTransportType(String transportType) {
    this.transportType = transportType;
  }
}

