package com.github.iesen.rabbitmq.plugin.mojo;

import com.github.iesen.rabbitmq.plugin.mojo.StartRabbitMojo;
import com.google.common.collect.Lists;
import org.apache.maven.plugin.logging.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import com.github.iesen.rabbitmq.plugin.RabbitManagerFactory;
import com.github.iesen.rabbitmq.plugin.api.RabbitMQRestClient;
import com.github.iesen.rabbitmq.plugin.manager.RabbitManager;
import com.github.iesen.rabbitmq.plugin.mojo.parameter.Exchange;
import com.github.iesen.rabbitmq.plugin.mojo.parameter.Queue;

import java.util.List;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RabbitManagerFactory.class, StartRabbitMojo.class})
public class StartRabbitMojoTest {

    private RabbitManager manager;
    private Log log;
    private StartRabbitMojo mojo;
    private RabbitMQRestClient rabbitMQRestClient;

    @Before
    public void setUp() throws Exception {
        mockStatic(RabbitManagerFactory.class);
        manager = mock(RabbitManager.class);
        log = mock(Log.class);
        rabbitMQRestClient = mock(RabbitMQRestClient.class);
        when(RabbitManagerFactory.create(log)).thenReturn(manager);
        // Defaults mocks to run successfully run the method
        when(manager.rabbitExtracted()).thenReturn(true);
        when(manager.isErlangInstalled()).thenReturn(true);
        when(manager.isRabbitRunning()).thenReturn(false);
        mojo = new StartRabbitMojo();
        mojo.setLog(log);
        mojo.setRabbitMQRestClient(rabbitMQRestClient);
    }

    @Test
    public void whenRabbitIsNotPresentDownloadsAndExtractsRabbit() throws Exception {
        // Fixture
        when(manager.rabbitExtracted()).thenReturn(false);
        // Execute
        mojo.execute();
        // Assert
        verify(manager).rabbitExtracted();
        verify(manager).extractServer();
        verify(manager).isErlangInstalled();
        verify(manager).isRabbitRunning();
        verify(manager).start(anyString(), anyBoolean());
        verifyNoMoreInteractions(manager);
    }

    @Test
    public void whenErlangIsNotPresentInstallsErlang() throws Exception {
        // Fixture
        when(manager.isErlangInstalled()).thenReturn(false);
        // Execute
        mojo.execute();
        // Assert
        verify(manager).rabbitExtracted();
        verify(manager).isErlangInstalled();
        verify(manager).installErlang();
        verify(manager).isRabbitRunning();
        verify(manager).start(anyString(), anyBoolean());
        Mockito.verifyNoMoreInteractions(manager);
    }

    @Test
    public void whenRabbitIsRunningStopsFirst() throws Exception {
        // Fixture
        when(manager.isRabbitRunning()).thenReturn(true);
        // Execute
        mojo.execute();
        // Assert
        verify(manager).rabbitExtracted();
        verify(manager).isErlangInstalled();
        verify(manager).isRabbitRunning();
        verify(manager).stop();
        verify(manager).start(anyString(), anyBoolean());
        Mockito.verifyNoMoreInteractions(manager);
    }

    @Test
    public void exchangesAndQueuesAreConfigured() throws Exception {
        List<Exchange> exchanges = Lists.newArrayList(new Exchange());
        List<Queue> queues = Lists.newArrayList(new Queue());
        mojo.setExchanges(exchanges);
        mojo.setQueues(queues);
        // Execute
        mojo.execute();
        // Assert
        verify(rabbitMQRestClient).createExchanges(exchanges);
        verify(rabbitMQRestClient).createQueues(queues);
        verifyNoMoreInteractions(rabbitMQRestClient);
    }
}