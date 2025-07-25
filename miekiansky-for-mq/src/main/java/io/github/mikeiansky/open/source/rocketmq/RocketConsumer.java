package io.github.mikeiansky.open.source.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.protocol.heartbeat.MessageModel;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 *
 * @author mike ian
 * @date 2025/7/18
 * @desc
 **/
public class RocketConsumer {

    public static void main(String[] args) throws InterruptedException, MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group_test");

        consumer.setNamesrvAddr("172.16.2.252:9876");

        // 集群消费模式
        consumer.setMessageModel(MessageModel.CLUSTERING);

        // 设置topic
        consumer.subscribe("topic_test", "*");

        // 注册回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                byte[] body = list.get(0).getBody();
                System.out.println("接收消息："+new String(body, StandardCharsets.UTF_8));
                return ConsumeOrderlyStatus.SUCCESS;

            }
        });

        // 启动消费者实例
        consumer.start();

        Thread.sleep(10000);
    }

}
