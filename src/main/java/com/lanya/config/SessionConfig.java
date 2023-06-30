package com.lanya.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * 解决子域session共享问题——Cookie序列化器CookieSerializer
 */
@Configuration
public class SessionConfig {

    // https://docs.spring.io/spring-session/docs/2.2.1.RELEASE/reference/html5/#api-cookieserializer
    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        // 设置cookie最大有效期
        // serializer.setCookieMaxAge(Integer.MAX_VALUE);
        // 设置cookie名称
        serializer.setCookieName("LANYA_SESSION");
        // 设置cookie路径
        serializer.setCookiePath("/");
        // serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        // 指定domain名称  这里直接设置成子域名即可
        serializer.setDomainName("ximalaya.com");
        // 设置成null 全域可取
        //serializer.setSameSite(null);
        return serializer;
    }

    /**
     * 官网强调:springSessionDefaultRedisSerializer方法名不能修改
     * @return
     */
    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }
}