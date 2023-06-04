package com.lanya.mytest;

import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.fastjson.JSON;

import cn.hutool.core.date.DateUnit;
import cn.hutool.json.JSONObject;
import com.lanya.es.service.EsRepository;
import com.lanya.spring.aop.rt.RecordMethodInvokeTime;
import com.lanya.spring.enable.RetryService;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private RetryService retryService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EsRepository esRepository;

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

    @GetMapping("/es/queryFromSize")
    public SearchResponse queryFromSizeEsData() throws Exception {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        return esRepository.queryPageSize(sourceBuilder);
    }

    @GetMapping("/es/searchAfter")
    public SearchResponse searchAfterEsData() throws Exception {
        return esRepository.searchAfter();
    }

    @GetMapping("/es/save")
    public IndexResponse saveEsData() throws Exception {
        return esRepository.save();
    }

    @GetMapping("/es/update")
    public IndexResponse updateEsData() throws Exception {
        return esRepository.update();
    }

}