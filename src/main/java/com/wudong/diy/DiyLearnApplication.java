package com.wudong.diy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class DiyLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiyLearnApplication.class, args);
    }
}
