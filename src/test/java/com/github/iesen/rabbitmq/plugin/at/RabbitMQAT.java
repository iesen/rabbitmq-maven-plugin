package com.github.iesen.rabbitmq.plugin.at;

import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;
import org.junit.After;
import org.junit.Test;

import java.io.File;

/**
 *
 */
public class RabbitMQAT {

    private Verifier verifier;

    @After
    public void tearDown() throws Exception {
        try {
            verifier.executeGoal("rabbitmq:stop");
        } catch (Exception e) {
            // RabbitMQ may not be running at this time
            System.out.println("Silently ignoring that rabbitmq is not running.");
        }
        verifier.resetStreams();
    }

    @Test
    public void testRabbitMqStarts() throws Exception {
        // Fixture
        File testDir = ResourceExtractor.simpleExtractResources(getClass(), "/rabbitmq-test-project");
        verifier = new Verifier(testDir.getAbsolutePath());
        verifier.deleteArtifact("tr.edu.anadolu.commons", "rabbitmq-maven-plugin-test", "0.0.1-SNAPSHOT", "jar");
        // Exercise
        verifier.executeGoal("rabbitmq:start");
        // Assert
        verifier.verifyErrorFreeLog();
    }
}
