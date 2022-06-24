package com.timsdt.core.configurations.domain;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProducerConfig extends Configuration {

    public KafkaProducerConfig(String host, String port, String topics) {
        super(host, port, topics);
    }

    public KafkaProducerConfig(String host, String port, String groupId, String topics) {
        super(host, port, groupId, topics);
    }

    public Type getType() {
        return Type.PRODUCER;
    }

    @Override
    public Properties getConfig() {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServer);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,   StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}
