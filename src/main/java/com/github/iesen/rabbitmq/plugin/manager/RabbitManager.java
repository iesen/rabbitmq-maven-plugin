package com.github.iesen.rabbitmq.plugin.manager;

import org.apache.maven.plugin.MojoExecutionException;

/**
 */
public interface RabbitManager {

    boolean rabbitExtracted();

    void extractServer() throws MojoExecutionException;

    boolean isRabbitRunning() throws MojoExecutionException;

    void stop() throws MojoExecutionException;

    void start(String port, boolean detached) throws MojoExecutionException;

    void installErlang() throws MojoExecutionException;

    boolean isErlangInstalled();
}
