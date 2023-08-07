package com.lanya.config.base64;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

@Component
@Slf4j
public class Base64EnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    public static final int DEFAULT_ORDER = ConfigFileApplicationListener.DEFAULT_ORDER + 10;

    private static final String ENV_NAME = "work_experience";

    private static String PREFIX = "secret.key";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        EncodesConfig encodesConfig = PropertiesBindUtil.bind(environment, EncodesConfig.class, PREFIX);

        // 无加密属性key不处理
        if (Objects.isNull(encodesConfig) || CollectionUtils.isEmpty(encodesConfig.getCryptoProperties())) {
            return;
        }
        Map<String, Object> addMap = new HashMap<>();
        // 处理需要解密的属性key
        for (String cryptoProperty : encodesConfig.getCryptoProperties()) {
            try {
                String cryptoPropertyValue = environment.getProperty(cryptoProperty);
                // 明文
                String decryptValue = new String(Base64Utils.decodeFromString(cryptoPropertyValue));
                addMap.putIfAbsent(cryptoProperty, decryptValue);
            } catch (Exception e) {
                log.error(String.format("postProcessEnvironment.error :cryptoProperty=%s", cryptoProperty), e);
            }
        }
        PropertySource<?> propertySource = new MapPropertySource(ENV_NAME, addMap);
        // 默认放在系统配置之前，优先级比系统配置高
        environment.getPropertySources().addBefore(StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME,
            propertySource);
    }

    @Override
    public int getOrder() {
        return DEFAULT_ORDER;
    }

}