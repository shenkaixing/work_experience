package com.lanya.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * nacos动态配置接收数据
 *
 * @author 兰崖 shenkaixing.skx
 * @date 2023/8/15 3:48 下午
 */
@Data
@Component
@ConfigurationProperties(prefix = "nacos.dynamic")
public class DynamicProperties {
    /**
     * 自定义配置
     */
    private String myvalue;

}
