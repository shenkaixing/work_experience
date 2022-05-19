package com.alibaba.dingtalk.diylearn.spirng.aop.rt;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义方法rt注解
 *
 * @author lanya
 * @date 2022/5/19 7:55 下午
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RecordMethodInvokeTime {
}
