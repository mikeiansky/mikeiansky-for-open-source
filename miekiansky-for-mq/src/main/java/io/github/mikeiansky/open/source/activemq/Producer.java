/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.mikeiansky.open.source.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;
import java.time.Duration;

/**
 * @author <a href="http://www.christianposta.com/blog">Christian Posta</a>
 */
public class Producer {
    private static final String BROKER_URL = "tcp://172.16.2.232:61616";

    private static final Boolean NON_TRANSACTED = false;
    private static final int NUM_MESSAGES_TO_SEND = 10;
    private static final long DELAY = 100;

    public static void main(String[] args) {
        String url = BROKER_URL;
        if (args.length > 0) {
            url = args[0].trim();
        }
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", url);
        Connection connection = null;

        try {

            connection = connectionFactory.createConnection();
            connection.start();

//            Session session = connection.createSession(NON_TRANSACTED, Session.AUTO_ACKNOWLEDGE);
            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
            Destination destination = session.createQueue("test-queue");
            MessageProducer producer = session.createProducer(destination);

            for (int i = 0; i < NUM_MESSAGES_TO_SEND; i++) {
                TextMessage message = session.createTextMessage("Message #" + i);
                System.out.println("Sending message #" + i);
                // 发送延迟消息
                message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 10 * 1000L);
                producer.send(message);
                Thread.sleep(DELAY);
            }

            producer.close();
//            session.rollback();
            session.commit();
            session.close();

        } catch (Exception e) {
            System.out.println("Caught exception!");
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    System.out.println("Could not close an open connection...");
                }
            }
        }
    }

}
