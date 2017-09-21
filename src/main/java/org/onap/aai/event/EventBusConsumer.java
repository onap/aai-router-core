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

import com.att.nsa.cambria.client.CambriaClientBuilders;
import com.att.nsa.cambria.client.CambriaClientBuilders.ConsumerBuilder;
import com.att.nsa.cambria.client.CambriaConsumer;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.ScheduledPollConsumer;
import org.onap.aai.logging.RouterCoreMsgs;
import org.onap.aai.cl.api.Logger;
import org.onap.aai.cl.eelf.LoggerFactory;
import org.onap.aai.cl.mdc.MdcContext;

import java.net.MalformedURLException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * The consumer component which is used to pull messages off of the event bus and send them to the
 * next processor in the route chain. This type of consumer is based off of a scheduled poller so
 * that events are pulled on a regular basis.
 */
public class EventBusConsumer extends ScheduledPollConsumer {

  private Logger logger = LoggerFactory.getInstance().getLogger(EventBusConsumer.class);
  private Logger auditLogger = LoggerFactory.getInstance().getAuditLogger(EventBusConsumer.class);
  private final EventBusEndpoint endpoint;

  private CambriaConsumer consumer;

  /**
   * EventBusConsumer Constructor.
   */
  public EventBusConsumer(EventBusEndpoint endpoint, Processor processor) {
    super(endpoint, processor);
    super.setDelay(endpoint.getPollingDelay());
    this.endpoint = endpoint;

    setScheduledExecutorService(new ScheduledThreadPoolExecutor(endpoint.getPoolSize()));

    String[] urls = endpoint.getUrl().split(",");

    List<String> urlList = null;

    if (urls != null) {
      urlList = Arrays.asList(urls);
    }

    try {

      ConsumerBuilder consumerBuilder = new CambriaClientBuilders.ConsumerBuilder()
          .usingHosts(urlList).onTopic(endpoint.getEventTopic())
          .knownAs(endpoint.getGroupName(), endpoint.getGroupId());

      String apiKey = endpoint.getApiKey();
      String apiSecret = endpoint.getApiSecret();

      if (apiKey != null && apiSecret != null) {
        consumerBuilder.authenticatedBy(endpoint.getApiKey(), endpoint.getApiSecret());
      }

      consumer = consumerBuilder.build();

    } catch (MalformedURLException | GeneralSecurityException e) {
      logger.error(RouterCoreMsgs.EVENT_CONSUMER_CREATION_EXCEPTION, e.getLocalizedMessage());
    }
  }

  /**
   * Method which is called by the Camel process on a scheduled basis. This specific implementation
   * reads messages off of the configured topic and schedules tasks to process them .
   * 
   * @return the number of messages that were processed off the event queue
   */
  @Override
  protected int poll() throws Exception {

    logger.debug("Checking for event on topic: " + endpoint.getEventTopic());

    int processCount = 0;

    Iterable<String> messages = null;

    messages = consumer.fetch();

    String topic = endpoint.getEventTopic();

    for (String message : messages) {
      Exchange exchange = endpoint.createExchange();
      exchange.getIn().setBody(message);
      getScheduledExecutorService().submit(new EventProcessor(exchange, topic));
      ++processCount;
    }
    return processCount;
  }

  protected void doStop() throws Exception {
    super.doStop();
    if (consumer != null) {
      consumer.close();
    }
  }

  protected void doShutdown() throws Exception {
    super.doShutdown();
    if (consumer != null) {
      consumer.close();
    }
  }

  /**
   * Class responsible for processing messages pulled off of the event bus.
   */
  private class EventProcessor implements Runnable {

    private Exchange message;

    private String topic;

    EventProcessor(Exchange message, String topic) {
      this.message = message;
      this.topic = topic;
    }

    public void run() {
      try {

        MdcContext.initialize(UUID.randomUUID().toString(), "DataRouter", "", "Event-Bus", "");

        // Sends the message to the next processor in the defined Camel route
        getProcessor().process(message);

        Message response = message.getOut();
        if (response != null) {
          logger.debug("Routing response: " + response.getBody());
        }

      } catch (Exception e) {
        logger.error(RouterCoreMsgs.EVENT_PROCESSING_EXCEPTION, e.getLocalizedMessage());
      } finally {
        // log exception if an exception occurred and was not handled
        if (message.getException() != null) {
          logger.info(RouterCoreMsgs.PROCESS_EVENT, topic, "FAILURE");
          auditLogger.info(RouterCoreMsgs.PROCESS_EVENT, topic, "FAILURE");
        } else {
          logger.info(RouterCoreMsgs.PROCESS_EVENT, topic, "SUCCESS");
          auditLogger.info(RouterCoreMsgs.PROCESS_EVENT, topic, "SUCCESS");
        }
      }
    }
  }
}
