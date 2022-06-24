package com.timsdt.core.configurations.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

public abstract class Configuration {
    public String bootstrapServer;
    protected String host;
    protected String port;
    public String topics;
    public String groupId;

    public Thread m_thread;

    public enum Type {
        CONSUMER, PRODUCER
    }

    public Configuration() {}

    public Configuration(String host, String port, String topics) {
        this.host = host;
        this.port = port;
        this.topics = topics;
        this.bootstrapServer = String.format("%s:%s", host, port);
    }
    public Configuration(String host, String port, String groupId, String topics) {
        this.host = host;
        this.port = port;
        this.topics = topics;
        this.groupId = groupId;
        this.bootstrapServer = String.format("%s:%s", host, port);
    }

    public abstract Type getType();
    public abstract Properties getConfig();
    public boolean invalid() {
        return StringUtils.isAnyBlank(host, port, topics);
    }
}
