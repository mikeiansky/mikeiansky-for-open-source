package io.github.mikeiansky.open.source.rocketmq;

import io.github.mikeiansky.open.source.activemq.Producer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

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
        producer.setNamesrvAddr("172.16.2.252:9876");

        // 启动实例
        producer.start();

        // 设置消息的topic,tag以及消息体
        Message msg = new Message("topic_test", "tag_test", "消息内容-004".getBytes(StandardCharsets.UTF_8));

        // 发送消息，并设置10s连接超时
        SendResult send = producer.send(msg, 10000);
        System.out.println("发送结果："+send);

        // 关闭实例
        producer.shutdown();
    }

}
