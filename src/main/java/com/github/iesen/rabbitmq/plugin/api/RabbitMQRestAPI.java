package com.github.iesen.rabbitmq.plugin.api;

import com.github.iesen.rabbitmq.plugin.api.model.BindingResource;
import com.github.iesen.rabbitmq.plugin.api.model.ExchangeResource;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import com.github.iesen.rabbitmq.plugin.api.model.QueueResource;

/**
 * RabbitMQ Management REST API functions
 */
public interface RabbitMQRestAPI {

    @PUT("/api/exchanges/{vhost}/{name}")
    public Response createExchange(@Body ExchangeResource exchange,
                                   @Path("vhost") String virtualHost,
                                   @Path("name") String exchangeName);

    @PUT("/api/queues/{vhost}/{name}")
    public Response createQueue(@Body QueueResource exchange,
                                @Path("vhost") String virtualHost,
                                @Path("name") String queueName);

    @POST("/api/bindings/{vhost}/e/{exchangeName}/q/{queueName}")
    public Response createBinding(@Body BindingResource binding,
                                  @Path("vhost") String virtualHost,
                                  @Path("exchangeName") String exchangeName,
                                  @Path("queueName") String queueName);

}
