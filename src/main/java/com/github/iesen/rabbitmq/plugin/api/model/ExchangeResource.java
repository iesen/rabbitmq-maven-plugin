package com.github.iesen.rabbitmq.plugin.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * RabbitMQ Management REST API Resource class
 * <p>
 * "type" : "direct",
 * "auto_delete" : "true",
 * "durable" : "true",
 * "internal": "false",
 * "arguments":[]
 * </p>
 */

public class ExchangeResource {

    private String type;
    @SerializedName("auto_delete")
    private Boolean autoDelete;
    private Boolean durable;
    private Boolean internal;

    public ExchangeResource() {
    }

    public ExchangeResource(String type) {
        this(type, true, false, false, new ArrayList<String>());
    }

    public ExchangeResource(String type, Boolean autoDelete, Boolean durable, Boolean internal, List<String> arguments) {
        this.type = type;
        this.autoDelete = autoDelete;
        this.durable = durable;
        this.internal = internal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getAutoDelete() {
        return autoDelete;
    }

    public void setAutoDelete(Boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    public Boolean getDurable() {
        return durable;
    }

    public void setDurable(Boolean durable) {
        this.durable = durable;
    }

    public Boolean getInternal() {
        return internal;
    }

    public void setInternal(Boolean internal) {
        this.internal = internal;
    }


    @Override
    public String toString() {
        return "ExchangeResource{" +
                "type='" + type + '\'' +
                ", autoDelete=" + autoDelete +
                ", durable=" + durable +
                ", internal=" + internal +
                '}';
    }
}
