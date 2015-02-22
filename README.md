#RabbitMQ Maven Plugin

A maven plugin that downloads, install and configures a RabbitMQ instance within maven lifecycle

###**The plugin is not yet in Maven Central, but it will soon be uploaded**

## Example Usage

Below is an example configuration for the plugin. You can add exchanges, queues and their binding in the configuration section.

```xml
<plugin>
    <groupId>com.github.iesen</groupId>
    <artifactId>rabbitmq-maven-plugin</artifactId>
    <version>0.1.0-SNAPSHOT</version>
    <configuration>
        <detached>true</detached>
        <exchanges>
            <exchange>
                <name>myexchange</name>
                <type>topic</type>
            </exchange>
            <queues>
                <queue>
                    <name>myqueue</name>
                    <bindings>
                        <binding>
                            <exchangeName>myexchange</exchangeName>
                            <routingKey>message.pingpong.*</routingKey>
                        </binding>
                    </bindings>
                </queue>
            </queues>
        </exchanges>
    </configuration>
</plugin>
```

##More Information

The plugin first checks if RabbitMQ is installed by this plugin before. If not, installs RabbitMQ to the home folder.
(also installs Erlang runtime if OS is Windows). Just after installation it enables the management plugin and applies
the settings specified in the plugin configuration.

##Supported Platforms

Windows and Mac OS is supported.

Linux will also be supported soon.




