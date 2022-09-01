package com.wudong.diy.mytest;

import java.util.concurrent.atomic.AtomicLong;

import cn.hutool.core.date.DateUnit;
import cn.hutool.json.JSONObject;
import com.wudong.diy.spirng.aop.rt.RecordMethodInvokeTime;
import com.wudong.diy.spirng.enable.RetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSON;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private RetryService retryService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "11";
    }

    @GetMapping("/hello")
    @RecordMethodInvokeTime(value = DateUnit.MS)
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/retry")
    @RecordMethodInvokeTime(value = DateUnit.MS)
    public String retry(@RequestParam(value = "param", defaultValue = "1") Integer param) throws Exception {
        return String.valueOf(retryService.handleBizData(param.intValue()));
    }

    @GetMapping("/httpException")
    @RecordMethodInvokeTime(value = DateUnit.MS)
    public String httpException() throws Exception {
        JSONObject postData = new JSONObject();
        ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity("http://www.vaeeeidu.com/eee", JSONObject.class,postData );
        return JSON.toJSONString(responseEntity.getBody());
    }

    @GetMapping("/timeOut")
    @RecordMethodInvokeTime(value = DateUnit.MS)
    public String timeOut() throws Exception {
        Thread.sleep(10000000);
        return "ok";
    }


}