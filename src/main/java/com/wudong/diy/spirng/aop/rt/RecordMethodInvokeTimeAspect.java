package com.wudong.diy.spirng.aop.rt;

import java.util.Date;
import java.util.Objects;

import cn.hutool.core.date.DateBetween;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 方法rt切面
 *
 * @author lanya
 * @date 2022/5/19 7:59 下午
 */
@Slf4j
@Aspect
@Component
public class RecordMethodInvokeTimeAspect {

    private RecordMethodInvokeTimeAspect() {}

    /**
     * 可自定义传入在打印日志时的前缀信息。
     *
     * @param msg
     */
    public static void setPrintMsg(String msg) {
        threadLocal.set(msg);
    }

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    /**
     * 切点
     */
    private static final String POINTCUT
        = "@annotation(com.wudong.diy.spirng.aop.rt.RecordMethodInvokeTime) && @annotation(recordMethodInvokeTime)";

    /**
     * 环绕方法，记录方法执行时间。
     *
     * @param proceedingJoinPoint
     * @param recordMethodInvokeTime
     */
    @Around(value = POINTCUT, argNames = "proceedingJoinPoint, recordMethodInvokeTime")
    public Object recordMethodInvokeTimeAspect(ProceedingJoinPoint proceedingJoinPoint,
        RecordMethodInvokeTime recordMethodInvokeTime) {

        Date startTime = new Date();
        String methodName = proceedingJoinPoint.getSignature().getName();
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("[{}] invoke failed.", methodName, throwable);
        }

        Date endTime = new Date();
        DateBetween dateBetween = DateBetween.create(startTime, endTime);

        // 如果传入了自定义的信息，则会拼接在时间信息的前面
        String print = "[{}] method invoke time is [{}], unit [{}]";
        if (Objects.nonNull(threadLocal.get())) {
            print = StringUtils.join(threadLocal.get(), print);
        }

        // 打印执行时间
        log.info(print, methodName, dateBetween.between(recordMethodInvokeTime.value()),
            recordMethodInvokeTime.value().name());
        return result;
    }

}
