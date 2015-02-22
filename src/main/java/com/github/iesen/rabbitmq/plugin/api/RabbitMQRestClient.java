package com.github.iesen.rabbitmq.plugin.api;

import com.github.iesen.rabbitmq.plugin.mojo.parameter.Exchange;
import com.github.iesen.rabbitmq.plugin.mojo.parameter.Queue;
import org.apache.maven.plugin.MojoExecutionException;

import java.util.List;

/**
 * RabbitMQ Management operations
 */
public interface RabbitMQRestClient {

    void createExchanges(List<Exchange> exchanges) throws MojoExecutionException;

    void createQueues(List<Queue> queues) throws MojoExecutionException;
}
