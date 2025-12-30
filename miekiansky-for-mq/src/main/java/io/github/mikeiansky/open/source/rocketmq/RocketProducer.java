package io.github.mikeiansky.open.source.rocketmq;

import io.github.mikeiansky.open.source.activemq.Producer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 *
 * @author mike ian
 * @date 2025/7/18
 * @desc
 **/
public class RocketProducer {
    private static final Logger logger = LoggerFactory.getLogger(RocketProducer.class);

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        // 声明group
        DefaultMQProducer producer = new DefaultMQProducer("group_test");

        // 声明namesrv地址
        producer.setNamesrvAddr("127.0.0.1:9876");

        // 启动实例
        producer.start();

        String ProxyEndpoint = "127.0.0.1:8081";


        // 设置消息的topic,tag以及消息体
        Message msg = new Message("TestTopic", "tag_test", "消息内容-009".getBytes(StandardCharsets.UTF_8));
        // 发送延迟消息
//        msg.setDelayTimeLevel(3);
        msg.setDeliverTimeMs(1000*9);

        producer.send(msg);

//        producer.send(msg, new MessageQueueSelector() {
//            @Override
//            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
//                return null;
//            }
//        }, "111");


//        producer.sendMessageInTransaction(msg, null);

        TransactionMQProducer tp = new TransactionMQProducer();
        tp.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                return null;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                return null;
            }
        });
//        tp.sendMessageInTransaction(msg, new Object());

//        msg.setFlag();

        // 发送消息，并设置10s连接超时
        SendResult send = producer.send(msg, 10000);
        System.out.println(new Date()+" , 发送结果："+send);

        // 关闭实例
        producer.shutdown();
    }

}
