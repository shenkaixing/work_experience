package com.lanya.config.base64;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertyResolver;

/**
 * springboot版本低于2就使用该绑定工具类
 */
public class PropertiesBindUtilSpringboot1 {

    public PropertiesBindUtilSpringboot1() {

    }

    public static <T> T bind(Environment environment, Class<T> targetClass, String prefix) {

        try {
            // 反射提取配置信息
            Class<?> resolverClass = Class.forName("org.springframework.boot.bind.RelaxedPropertyResolver");
            Constructor<?> resolverConstructor = resolverClass.getDeclaredConstructor(PropertyResolver.class);
            Method getSubPropertiesMethod = resolverClass.getDeclaredMethod("getSubProperties", String.class);
            Object resolver = resolverConstructor.newInstance(environment);
            @SuppressWarnings("unchecked")
            Map<String, Object> properties = (Map<String, Object>)getSubPropertiesMethod.invoke(resolver, "");
            // 创建结果类
            T target = targetClass.newInstance();
            // 反射使用 org.springframework.boot.bind.RelaxedDataBinder
            Class<?> binderClass = Class.forName("org.springframework.boot.bind.RelaxedDataBinder");
            Constructor<?> binderConstructor = binderClass.getDeclaredConstructor(Object.class, String.class);
            Method bindMethod = binderClass.getMethod("bind", PropertyValues.class);
            // 创建 binder 并绑定数据
            Object binder = binderConstructor.newInstance(target, prefix);
            bindMethod.invoke(binder, new MutablePropertyValues(properties));
            return target;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}