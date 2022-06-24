package com.timsdt.core.configurations;

import com.timsdt.core.configurations.domain.Configuration;
import com.timsdt.core.configurations.domain.KafkaConsumerConfig;
import com.timsdt.core.configurations.domain.KafkaProducerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.time.Duration;
import java.util.Arrays;

public class KafkaService {
    public KafkaConsumerConfig consumerConfig;
    public KafkaProducerConfig producerConfig;

    public synchronized void start() {
        startConfiguration(producerConfig);
        startConfiguration(consumerConfig);
    }

    private void startConfiguration(Configuration configuration) {
        if (configuration == null || configuration.invalid()) {
            return;
        }
        if (configuration.m_thread != null) {
            return;
        }
        configuration.m_thread = new Thread() {
            @Override
            public void run() {
                System.out.printf("KafkaService :: startConfiguration kafka %s?topics=%s%n", configuration.bootstrapServer,
                        configuration.topics);
                if (Configuration.Type.CONSUMER.equals(configuration.getType())) {
                    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(configuration.getConfig());
                    consumer.subscribe(Arrays.asList(configuration.topics));
                    while (true) {
                        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                        for (ConsumerRecord<String, String> record : records) {
                            System.out.println("Key: " + record.key() + ", Value:" + record.value());
                            System.out.println("Partition:" + record.partition() + ",Offset:" + record.offset());
                        }
                    }
                }
                if (Configuration.Type.PRODUCER.equals(configuration.getType())) {
                    KafkaProducer<String,String> producer = new KafkaProducer<>(configuration.getConfig());
                    System.out.println("created producer");
                }
            }
        };
        configuration.m_thread.start();
    }

    public void setupProducer(String host, String port, String topics) {
        producerConfig = new KafkaProducerConfig(host, port, topics);
    }

    public void setupConsumer(String host, String port, String groupId, String topics) {
        consumerConfig = new KafkaConsumerConfig(host, port, groupId, topics);
    }
}
