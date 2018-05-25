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
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.onap.aai.event.client.KafkaEventConsumer;

/**
 * Represents a EventBus endpoint.
 */
@UriEndpoint(scheme = "kafka-event-bus", syntax = "kafka-event-bus:name",
    consumerClass = EventBusConsumer.class, title = "kafka-event-bus")
public class KafkaEventBusEndpoint extends AbstractEventBusEndpoint {
  @UriParam(label = "url")
  @Metadata(required = "true")
  private String url;
  @UriParam(label = "eventTopic")
  @Metadata(required = "true")
  private String eventTopic;
  @UriParam(label = "consumerGroup")
  @Metadata(required = "true")
  private String consumerGroup;
  @UriParam(label = "poolSize")
  @Metadata(required = "true", defaultValue="20")
  private int poolSize = 20;
  @UriParam(label = "pollingDelay")
  @Metadata(required = "true", defaultValue="30000")
  private int pollingDelay = 30000;

  private KafkaEventConsumer consumer;

  public KafkaEventBusEndpoint(String uri, KafkaEventBusComponent component) {
    super(uri, component);
  }

  @Override
  public Producer createProducer() throws Exception {
    return new EventBusProducer(this);
  }

  @Override
  public Consumer createConsumer(Processor processor) throws Exception {
    consumer = new KafkaEventConsumer(url, eventTopic, consumerGroup);
    return new EventBusConsumer(this, processor, consumer);
  }

  @Override
  public boolean isSingleton() {
    return false;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  String getEventTopic() {
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

  @Override
  int getPoolSize() {
    return poolSize;
  }

  public void setPoolSize(int poolSize) {
    this.poolSize = poolSize;
  }

  @Override
  void close() {
    consumer.close();
  }

  @Override
  int getPollingDelay() {
    return pollingDelay;
  }

  public void setPollingDelay(int pollingDelay) {
    this.pollingDelay = pollingDelay;
  }






}
