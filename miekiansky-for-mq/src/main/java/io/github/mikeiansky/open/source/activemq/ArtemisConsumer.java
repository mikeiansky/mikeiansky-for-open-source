package io.github.mikeiansky.open.source.activemq;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 *
 * @author mike ian
 * @date 2025/7/17
 * @desc
 **/
public class ArtemisConsumer {

    public static void main(String[] args) {

        // 主节点地址（HA 模式只需要一个地址）
        String brokerURL = "tcp://172.16.2.252:61616";

        // 用户名和密码
        String user = "admin";
        String password = "admin";

        // 队列名称
        String queueName = "testQueue";

        try (ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL, user, password);
             Connection connection = factory.createConnection()) {

            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(queueName);
            MessageConsumer consumer = session.createConsumer(queue);

            System.out.println("Waiting for messages...");

            // 一直监听
            while (true) {
                Message message = consumer.receive(5000); // 5秒超时
                if (message != null && message instanceof TextMessage) {
                    TextMessage text = (TextMessage) message;
                    System.out.println("Received: " + text.getText());
                } else {
                    System.out.println("No message received.");
                }
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

}
