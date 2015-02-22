package com.github.iesen.rabbitmq.plugin.mojo.parameter;

/**
 *
 */
public class Binding {

    private String exchangeName;
    private String routingKey;

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }
}
