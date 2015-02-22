package com.github.iesen.rabbitmq.plugin.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * RabbitMQ Management REST API Resource class
 */
public class QueueResource {

    /* Default is false */
    private Boolean durable = false;
    /* Default is false */
    @SerializedName("auto_delete")
    private Boolean autoDelete = false;
    private List<String> arguments;

    public QueueResource() {
    }

    public QueueResource(Boolean durable, Boolean autoDelete, List<String> arguments) {
        this.durable = durable;
        this.autoDelete = autoDelete;
        this.arguments = arguments;
    }

    public Boolean getDurable() {
        return durable;
    }

    public void setDurable(Boolean durable) {
        this.durable = durable;
    }

    public Boolean getAutoDelete() {
        return autoDelete;
    }

    public void setAutoDelete(Boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "QueueResource{" +
                "durable=" + durable +
                ", autoDelete=" + autoDelete +
                ", arguments=" + arguments +
                '}';
    }
}
