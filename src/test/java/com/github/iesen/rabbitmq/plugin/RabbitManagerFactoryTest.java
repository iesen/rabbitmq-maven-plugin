package com.github.iesen.rabbitmq.plugin;

import com.github.iesen.rabbitmq.plugin.RabbitManagerFactory;
import org.apache.commons.lang3.SystemUtils;
import org.apache.maven.plugin.logging.Log;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import com.github.iesen.rabbitmq.plugin.manager.MacRabbitManager;
import com.github.iesen.rabbitmq.plugin.manager.RabbitManager;
import com.github.iesen.rabbitmq.plugin.manager.WindowsRabbitManager;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RabbitManagerFactory.class, SystemUtils.class})
public class RabbitManagerFactoryTest {

    @Test
    public void createUsesWindowsWhenOnWindows() throws Exception {
        Log log = mock(Log.class);
        mockStatic(SystemUtils.class);
        Whitebox.setInternalState(SystemUtils.class, "IS_OS_MAC_OSX", false);
        Whitebox.setInternalState(SystemUtils.class, "IS_OS_WINDOWS", true);
        RabbitManager manager = RabbitManagerFactory.create(log);
        Assert.assertTrue(manager instanceof WindowsRabbitManager);
    }

    @Test
    public void createUsesMacOSXWhenOnMacOSX() throws Exception {
        Log log = mock(Log.class);
        mockStatic(SystemUtils.class);
        Whitebox.setInternalState(SystemUtils.class, "IS_OS_MAC_OSX", true);
        Whitebox.setInternalState(SystemUtils.class, "IS_OS_WINDOWS", false);
        RabbitManager manager = RabbitManagerFactory.create(log);
        Assert.assertTrue(manager instanceof MacRabbitManager);
    }
}