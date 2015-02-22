package com.github.iesen.rabbitmq.plugin.mojo;

import com.github.iesen.rabbitmq.plugin.mojo.StartRabbitMojo;
import com.github.iesen.rabbitmq.plugin.mojo.StopRabbitMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import com.github.iesen.rabbitmq.plugin.RabbitManagerFactory;
import com.github.iesen.rabbitmq.plugin.manager.RabbitManager;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RabbitManagerFactory.class, StartRabbitMojo.class})
public class StopRabbitMojoTest {

    private RabbitManager manager;
    private Log log;
    private StopRabbitMojo mojo;

    @Before
    public void setUp() throws Exception {
        mockStatic(RabbitManagerFactory.class);
        manager = mock(RabbitManager.class);
        log = mock(Log.class);
        when(RabbitManagerFactory.create(log)).thenReturn(manager);
        // Defaults mocks to run successfully run the method
        when(manager.isRabbitRunning()).thenReturn(true);
        when(manager.rabbitExtracted()).thenReturn(true);
        mojo = new StopRabbitMojo();
        mojo.setLog(log);
    }

    @Test
    public void stopsRabbitIfEverythingOk() throws Exception {
        mojo.execute();
        // Assert
        verify(manager).stop();
    }

    @Test(expected = MojoExecutionException.class)
    public void throwsErrorWhenRabbitIsNotRunning() throws Exception {
        when(manager.isRabbitRunning()).thenReturn(false);
        mojo.execute();
    }

    @Test(expected = MojoExecutionException.class)
    public void throwsErrorWhenRabbitIsNotExtracted() throws Exception {
        when(manager.rabbitExtracted()).thenReturn(false);
        mojo.execute();
    }
}