package com.lanya.controller;

import com.lanya.config.DynamicProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 多数据源测试
 *
 * @author 兰崖 shenkaixing.skx
 * @date 2023/8/7 11:18 下午
 */
@RestController
@Slf4j
public class NacosController {

    @Autowired
    private DynamicProperties dynamicProperties;

    @GetMapping(value = "/test/feng/test")
    public Object testValue() {
        log.info(dynamicProperties.getMyvalue());
        return dynamicProperties.getMyvalue();
    }
}
