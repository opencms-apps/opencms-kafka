package com.timsdt.core.configurations;

import com.timsdt.core.configurations.domain.Configuration;
import com.timsdt.core.configurations.domain.KafkaConsumerConfig;
import com.timsdt.core.configurations.domain.KafkaProducerConfig;
import org.opencms.main.OpenCms;

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
        OpenCms.getExecutor().execute(new Thread() {
            @Override
            public void run() {
                try {
                    System.out.printf("KafkaService :: startConfiguration kafka %s?topics=%s%n", configuration.bootstrapServer,
                            configuration.topics);
                    if (Configuration.Type.CONSUMER.equals(configuration.getType())) {

                    }
                    if (Configuration.Type.PRODUCER.equals(configuration.getType())) {

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void setupProducer(String host, String port, String topics) {
        producerConfig = new KafkaProducerConfig(host, port, topics);
    }

    public void setupProducer(String host, String port, String groupId, String topics) {
        producerConfig = new KafkaProducerConfig(host, port, groupId, topics);
    }

    public void setupConsumer(String host, String port, String groupId, String topics) {
        consumerConfig = new KafkaConsumerConfig(host, port, groupId, topics);
    }

    public void setupConsumer(String host, String port, String topics) {
        consumerConfig = new KafkaConsumerConfig(host, port, topics);
    }
}
