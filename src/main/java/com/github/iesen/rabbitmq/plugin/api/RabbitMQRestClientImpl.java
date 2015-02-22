package com.github.iesen.rabbitmq.plugin.api;

import com.github.iesen.rabbitmq.plugin.api.model.BindingResource;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Response;
import com.github.iesen.rabbitmq.plugin.api.model.ExchangeResource;
import com.github.iesen.rabbitmq.plugin.api.model.QueueResource;
import com.github.iesen.rabbitmq.plugin.mojo.parameter.Binding;
import com.github.iesen.rabbitmq.plugin.mojo.parameter.Exchange;
import com.github.iesen.rabbitmq.plugin.mojo.parameter.Queue;

import java.io.IOException;
import java.util.List;

/**
 * RabbitMQ Management operations implementation
 */
public class RabbitMQRestClientImpl implements RabbitMQRestClient {

    public static final String DEFAULT_VHOST = "/";
    private static final int MANAGEMENT_PLUGIN_WAIT_TIMEOUT = 10000;
    private String url;
    private String username;
    private String password;
    private final Log log;
    private RestAdapter restAdapter;
    private boolean managementPluginStarted;

    public RabbitMQRestClientImpl(String url, String username, String password, Log log) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.log = log;
        final String encodedAuthHeader = createBasicAuthHeader();
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(url);
        builder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Authorization", encodedAuthHeader);
            }
        });
        restAdapter = builder.build();
    }

    private String createBasicAuthHeader() {
        return "Basic " + Base64.encodeBase64String((this.username + ":" + this.password).getBytes());
    }


    @Override
    public void createExchanges(List<Exchange> exchanges) throws MojoExecutionException {
        waitForManagementToStart();
        RabbitMQRestAPI api = restAdapter.create(RabbitMQRestAPI.class);
        for (Exchange exchange : exchanges) {
            ExchangeResource exchangeResource = new ExchangeResource(exchange.getType());
            Response response = api.createExchange(exchangeResource, DEFAULT_VHOST, exchange.getName());
            log.debug("Create exchange returned: " + response.getStatus());
        }
    }

    @Override
    public void createQueues(List<Queue> queues) throws MojoExecutionException {
        waitForManagementToStart();
        RabbitMQRestAPI api = restAdapter.create(RabbitMQRestAPI.class);
        for (Queue queue : queues) {
            QueueResource queueResource = new QueueResource();
            Response response = api.createQueue(queueResource, DEFAULT_VHOST, queue.getName());
            log.debug("Create queue returned: " + response.getStatus());
            for (Binding binding : queue.getBindings()) {
                BindingResource bindingResource = new BindingResource(binding.getRoutingKey());
                Response bndResponse = api.createBinding(bindingResource, DEFAULT_VHOST, binding.getExchangeName(), queue.getName());
                log.debug("Create binding returned: " + bndResponse.getStatus());
            }
        }

    }

    private boolean waitForManagementToStart() throws MojoExecutionException {
        if (managementPluginStarted) {
            return true;
        }
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(this.url);
        get.setConfig(RequestConfig.custom().setSocketTimeout(MANAGEMENT_PLUGIN_WAIT_TIMEOUT).build());
        try {
            log.debug("Connecting API");
            client.execute(get);
            managementPluginStarted = true;
            return true;
        } catch (IOException e) {
            throw new MojoExecutionException("RabbitMQ API could not start");
        }
    }
}
