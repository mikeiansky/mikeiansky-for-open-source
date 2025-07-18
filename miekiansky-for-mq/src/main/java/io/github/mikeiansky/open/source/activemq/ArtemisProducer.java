package io.github.mikeiansky.open.source.activemq;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 *
 * @author mike ian
 * @date 2025/7/17
 * @desc
 **/
public class ArtemisProducer {

    public static void main(String[] args) throws JMSException {

        ConnectionFactory factory = new ActiveMQConnectionFactory(
                "tcp://172.16.2.252:61616"
        );
        Connection connection = factory.createConnection("admin", "admin");
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("testQueue");
        MessageProducer producer = session.createProducer(queue);
        producer.send(session.createTextMessage("Hello Artemis!"));

        System.out.println("send complete ... ");

    }

}
