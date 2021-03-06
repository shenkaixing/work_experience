package com.wudong.diy.mytest;

import java.util.concurrent.atomic.AtomicLong;

import cn.hutool.core.date.DateUnit;
import com.wudong.diy.spirng.aop.rt.RecordMethodInvokeTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "11";
    }

    @GetMapping("/hello")
    @RecordMethodInvokeTime(value = DateUnit.MS)
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }
}