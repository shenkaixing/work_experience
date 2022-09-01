package com.wudong.diy.spirng.mvc.client;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * httpClient
 * @author 兰崖 shenkaixing.skx
 * @date 2022/9/1 11:47 上午
 */
@Configuration
public class HttpClientConfig {

    @Bean
    public RestTemplate restTemplate() {

        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient());
        // 从连接池获取连接的超时时间
        httpRequestFactory.setConnectionRequestTimeout(3000);
        // 客户端和服务器建立连接的超时时间
        httpRequestFactory.setConnectTimeout(3000);
        // 从服务器读取数据的超时时间
        httpRequestFactory.setReadTimeout(5000);

        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);

        return restTemplate;
    }

    /**
     * http链接池
     * 服务器返回数据(response)的时间，超过该时间抛出read timeout
     *
     * @return
     */
    public HttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.getSocketFactory())
            .register("https", SSLConnectionSocketFactory.getSocketFactory())
            .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        //设置整个连接池最大连接数 根据自己的场景决定
        connectionManager.setMaxTotal(300);
        //最大单个路由是对maxTotal的细分
        connectionManager.setDefaultMaxPerRoute(150);
        RequestConfig requestConfig = RequestConfig.custom()
            //服务器返回数据(response)的时间，超过该时间抛出read timeout
            .setSocketTimeout(3500)
            //连接上服务器(握手成功)的时间，超出该时间抛出connect timeout
            .setConnectTimeout(2000)
            //从连接池中获取连接的超时时间，超过该时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
            .setConnectionRequestTimeout(1000)
            .build();
        return HttpClientBuilder.create()
            .setDefaultRequestConfig(requestConfig)
            .setConnectionManager(connectionManager)
            .build();
    }
}
