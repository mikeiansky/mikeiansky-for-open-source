package io.github.mikeiansky.sample.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author mike ian
 * @date 2024/12/25
 * @desc
 **/
public class KafkaProducerDemo {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.16.2.254:9092");
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 10);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        ProducerRecord<String, String> record = new ProducerRecord<>("test-topic", "key7", "value7");
//        ProducerRecord<String, String> record = new ProducerRecord<>("my_topic", null, "value1");
        producer.send(record);
        record = new ProducerRecord<>("test-topic", "key18", "value18");
        producer.send(record);
        record = new ProducerRecord<>("test-topic", "key19", "value19");
        producer.send(record);
        producer.flush();
    }

}
