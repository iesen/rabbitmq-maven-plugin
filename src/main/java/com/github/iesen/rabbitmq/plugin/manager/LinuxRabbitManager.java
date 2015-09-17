package com.github.iesen.rabbitmq.plugin.manager;

import static com.github.iesen.rabbitmq.plugin.RabbitMQConstants.RABBITMQ_HOME;
import static com.github.iesen.rabbitmq.plugin.RabbitMQConstants.RABBITMQ_PARENT_DIR;
import static com.github.iesen.rabbitmq.plugin.RabbitMQConstants.RABBITMQ_VERSION;

import com.google.common.collect.Lists;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 */
public class LinuxRabbitManager extends MacRabbitManager {

    private static final int BUFFER = 2048;
    private final Log log;

    public LinuxRabbitManager(Log log) {
        super(log);
        this.log = log;
    }

    @Override
    public boolean rabbitExtracted() {
        File rabbitHome = new File(RABBITMQ_HOME);
        log.info("Checking " + rabbitHome.getAbsolutePath());
        boolean exists = rabbitHome.exists();
        log.info("RabbitMQ " + (exists ? "found" : "not found"));
        return exists;
    }

    @Override
    public void extractServer() throws MojoExecutionException {
        try {
            String rabbitDownloadUrl = "https://github.com/rabbitmq/rabbitmq-server/releases/download/rabbitmq_v" + RABBITMQ_VERSION +
               "/rabbitmq-server-generic-unix-" + RABBITMQ_VERSION + ".tar.gz";
            log.debug("Downloading rabbitmq from " + rabbitDownloadUrl);
            FileUtils.download(rabbitDownloadUrl, RABBITMQ_PARENT_DIR + File.separator + "rabbitmq-server-mac-standalone-" + RABBITMQ_VERSION + ".tar.gz");
            log.debug("Extracting downloaded files");
            FileUtils.extractTarGzip(RABBITMQ_PARENT_DIR + File.separator + "rabbitmq-server-mac-standalone-" + RABBITMQ_VERSION + ".tar.gz", RABBITMQ_PARENT_DIR);
            // Give permissions
            ProcessBuilder permissionProcess = new ProcessBuilder("/bin/chmod", "-R", "777", RABBITMQ_PARENT_DIR);
            log.debug("Permission command " + permissionProcess.command());
            Process permission = permissionProcess.start();
            permission.waitFor();
            // Enable management
            ProcessBuilder managementEnabler = new ProcessBuilder(RABBITMQ_HOME + File.separator + "sbin" + File.separator + "rabbitmq-plugins", "enable", "rabbitmq_management");
            log.debug("Enable management " + managementEnabler.command());
            Process mgmt = managementEnabler.start();
            mgmt.waitFor();
        } catch (IOException e) {
            throw new MojoExecutionException("Error extracting server", e);
        } catch (InterruptedException e) {
            throw new MojoExecutionException("Error executing process", e);
        }
    }


    @Override
    public void installErlang() throws MojoExecutionException {
        throw new MojoExecutionException("unsuported operating system");
    }

    @Override
    public boolean isErlangInstalled() throws MojoExecutionException {
        ProcessBuilder permissionProcess = new ProcessBuilder("erl");
        log.debug("Permission command " + permissionProcess.command());
        Process permission = null;
        try {
            permission = permissionProcess.start();
            permission.waitFor();
        } catch (IOException e) {
            throw new MojoExecutionException("Erlang is not installed", e);
        } catch (InterruptedException e){
            throw new MojoExecutionException("Erlang is not installed", e);
        }
        return true;
        //todo
    }

}
