package com.lanya.config.base64;

import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.util.ClassUtils;

/**
 * springboot 2.0 以上使用该配置绑定属性
 */
public class PropertiesBindUtil {

    public PropertiesBindUtil() {

    }

    public static <T> T bind(Environment environment, Class<T> targetClass, String prefix) {
        try {
            if (environment == null) {
                environment = new StandardEnvironment();
            }

            Binder binder = Binder.get((Environment)environment);
            Bindable<T> bindable = Bindable.of(targetClass);
            BindResult<T> bindResult = binder.bind(prefix, bindable);
            if (bindResult.isBound()) {
                return bindResult.get();
            } else {
                return ClassUtils.hasConstructor(targetClass, new Class[0]) ? targetClass.newInstance() : null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}