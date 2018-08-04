package com.target.api;

import co.paralleluniverse.fibers.httpasyncclient.FiberCloseableHttpAsyncClient;
import com.google.common.collect.ImmutableList;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpClientConfiguration {

    private static final Logger log = LoggerFactory.getLogger(HttpClientConfiguration.class);


    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
        restTemplate.setInterceptors(ImmutableList.of((request, body, execution) -> {
            //byte[] token = Base64.encodeBase64((format("%s:%s", environment.getProperty("fake.username"), environment.getProperty("fake.password"))).getBytes());
            // request.getHeaders().add("Authorization", format("Basic %s", new String(token)));

            return execution.execute(request, body);
        }));

        return restTemplate;
    }

    @Bean
    public HttpClient httpClient() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(50);
        connectionManager.setDefaultMaxPerRoute(50);
        return HttpClientBuilder
                .create()
                .setConnectionManager(connectionManager)
                .build();
    }

    @Bean
    public CloseableHttpAsyncClient getClient() {
        CloseableHttpAsyncClient client =
                FiberCloseableHttpAsyncClient.wrap(HttpAsyncClients.
                        custom().
                        setMaxConnPerRoute(50).
                        setMaxConnTotal(50).
                        build());
        client.start();
        return client;
    }
}