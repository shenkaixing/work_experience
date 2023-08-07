package com.lanya;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRetry
@EnableAsync
@MapperScan("com.lanya.dao.mapper")
@EnableRedisHttpSession
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DiyLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiyLearnApplication.class, args);
    }
}
