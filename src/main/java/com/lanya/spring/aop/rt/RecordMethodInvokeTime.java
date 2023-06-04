package com.lanya.spring.aop.rt;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.hutool.core.date.DateUnit;

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
    // 这里需要传入记录时间的单位，可以秒、分、小时来记录时间。
    DateUnit value();
}
