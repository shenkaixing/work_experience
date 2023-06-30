package com.lanya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRetry
@EnableAsync
@EnableRedisHttpSession
@SpringBootApplication
public class DiyLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiyLearnApplication.class, args);
    }
}
