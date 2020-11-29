package com.github.iesen.rabbitmq.plugin;

import com.github.iesen.rabbitmq.plugin.manager.LinuxRabbitManager;
import org.apache.commons.lang3.SystemUtils;
import org.apache.maven.plugin.logging.Log;
import com.github.iesen.rabbitmq.plugin.manager.MacRabbitManager;
import com.github.iesen.rabbitmq.plugin.manager.RabbitManager;
import com.github.iesen.rabbitmq.plugin.manager.WindowsRabbitManager;

/**
 */
public class RabbitManagerFactory {

    public static RabbitManager create(Log log) {
        if (SystemUtils.IS_OS_MAC_OSX) {
            return new MacRabbitManager(log);
        }
        if (SystemUtils.IS_OS_WINDOWS) {
            return new WindowsRabbitManager(log);
        }
        if (SystemUtils.IS_OS_LINUX) {
            return new LinuxRabbitManager(log);
        }
        throw new IllegalStateException("Unsupported os: " + System.getProperty("os.name"));
    }

}
