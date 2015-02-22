package com.github.iesen.rabbitmq.plugin.mojo;


import com.github.iesen.rabbitmq.plugin.mojo.parameter.Queue;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import com.github.iesen.rabbitmq.plugin.RabbitMQConstants;
import com.github.iesen.rabbitmq.plugin.RabbitManagerFactory;
import com.github.iesen.rabbitmq.plugin.api.RabbitMQRestClient;
import com.github.iesen.rabbitmq.plugin.api.RabbitMQRestClientImpl;
import com.github.iesen.rabbitmq.plugin.manager.RabbitManager;
import com.github.iesen.rabbitmq.plugin.mojo.parameter.Exchange;

import java.util.List;

/**
 * Starts rabbitmq and configures exchanges, queues and bindings.
 * Downloads rabbitmq if it is not present.
 */
@Mojo(name = "start")
public class StartRabbitMojo extends AbstractMojo {

    @Parameter(defaultValue = RabbitMQConstants.RABBITMQ_DEFAULT_PORT)
    private String port;
    @Parameter(defaultValue = "true")
    private boolean detached;
    @Parameter
    private List<Exchange> exchanges;
    @Parameter
    private List<Queue> queues;
    private RabbitMQRestClient rabbitMQRestClient;

    public StartRabbitMojo() {
        rabbitMQRestClient = new RabbitMQRestClientImpl("http://localhost:15672", "guest", "guest", getLog());
    }

    public void execute() throws MojoExecutionException {
        RabbitManager manager = RabbitManagerFactory.create(getLog());
        if (!manager.rabbitExtracted()) {
            manager.extractServer();
        }
        if (!manager.isErlangInstalled()) {
            manager.installErlang();
        }
        if (manager.isRabbitRunning()) {
            getLog().info("RabbitMQ already running...");
            getLog().info("Restarting RabbitMQ...");
            manager.stop();
        }
        manager.start(port, detached);
        // Configure exchanges and queues
        if (exchanges != null) {
            getLog().debug("Exchanges : " + ToStringBuilder.reflectionToString(exchanges));
            rabbitMQRestClient.createExchanges(exchanges);
        }
        if (queues != null) {
            getLog().debug("Queues : " + ToStringBuilder.reflectionToString(queues));
            rabbitMQRestClient.createQueues(queues);
        }
    }

    public void setRabbitMQRestClient(RabbitMQRestClient rabbitMQRestClient) {
        this.rabbitMQRestClient = rabbitMQRestClient;
    }

    public void setExchanges(List<Exchange> exchanges) {
        this.exchanges = exchanges;
    }

    public void setQueues(List<Queue> queues) {
        this.queues = queues;
    }
}

