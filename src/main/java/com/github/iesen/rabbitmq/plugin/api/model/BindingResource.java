package com.github.iesen.rabbitmq.plugin.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * RabbitMQ Management REST API Resource class
 */
public class BindingResource {

    @SerializedName("routing_key")
    private String routingKey;
    private List<String> arguments;

    public BindingResource() {
    }

    public BindingResource(String routingKey) {
        this(routingKey, new ArrayList<String>());
    }

    public BindingResource(String routingKey, List<String> arguments) {
        this.routingKey = routingKey;
        this.arguments = arguments;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "BindingResource{" +
                "routingKey='" + routingKey + '\'' +
                ", arguments=" + arguments +
                '}';
    }
}
