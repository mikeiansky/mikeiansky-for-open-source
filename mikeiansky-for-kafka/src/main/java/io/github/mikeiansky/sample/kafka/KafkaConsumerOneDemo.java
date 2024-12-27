package io.github.mikeiansky.sample.kafka;



import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @author mike ian
 * @date 2024/12/25
 * @desc
 **/
public class KafkaConsumerOneDemo {

    public static void main(String[] args) {
        // 配置消费者属性

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.16.2.252:9092"); // Kafka 集群地址
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "3"); // 消费者组 ID
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // 从最早的记录开始消费

        // 创建 KafkaConsumer 实例
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            // 订阅主题
            consumer.subscribe(Collections.singletonList("test-topic"));

            // 持续拉取消息
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Received message: key=%s, value=%s, topic=%s, partition=%d, offset=%d%n",
                            record.key(), record.value(), record.topic(), record.partition(), record.offset());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
