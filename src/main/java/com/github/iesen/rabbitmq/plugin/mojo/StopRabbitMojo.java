package com.github.iesen.rabbitmq.plugin.mojo;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import com.github.iesen.rabbitmq.plugin.RabbitManagerFactory;
import com.github.iesen.rabbitmq.plugin.manager.RabbitManager;

/**
 * Stops rabbitmq server
 */
@Mojo(name = "stop")
public class StopRabbitMojo extends AbstractMojo {


    public void execute() throws MojoExecutionException {
        RabbitManager manager = RabbitManagerFactory.create(getLog());
        if (!manager.isRabbitRunning()) {
            throw new MojoExecutionException("RabbitMQ is not started");
        }
        if (!manager.rabbitExtracted()) {
            throw new MojoExecutionException("RabbitMQ is not extracted");
        }
        getLog().info("Stopping RabbitMQ...");
        manager.stop();
    }

}
