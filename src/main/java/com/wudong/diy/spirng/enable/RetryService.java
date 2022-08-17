package com.wudong.diy.spirng.enable;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 *
 * @author 兰崖 shenkaixing.skx
 * @date 2022/8/17 8:58 下午
 */
@Service
@Slf4j
public class RetryService {

    /**
     * interceptor：可以通过该参数，指定方法拦截器的bean名称
     * value：抛出指定异常才会重试
     * include：和value一样，默认为空，当exclude也为空时，默认所以异常
     * exclude：指定不处理的异常
     * maxAttempts：最大重试次数，默认3次
     * backoff：重试等待策略，默认使用@Backoff，@Backoff的value默认为1000L，我们设置为2000L；multiplier（指定延迟倍数）默认为0，表示固定暂停1
     * 秒后进行重试，如果把multiplier设置为1.5，则第一次重试为2秒，第二次为3秒，第三次为4.5秒。
     * @param param
     * @return
     * @throws Exception
     */
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000L, multiplier = 1.5))
    public boolean handleBizData(int param) throws Exception{
        // 方法体
        if (1>0) {
            log.error("RetryService.retry");
            throw new Exception("业务系统异常");
        }
        return false;
    }

    /**
     * @Recover注解的方法，需要特别注意的是：
     * 1、方法的返回值必须与@Retryable方法一致
     * 2、方法的第一个参数，必须是Throwable类型的，建议是与@Retryable配置的异常一致，其他的参数，需要与@Retryable方法的参数一致
     * @param e
     * @param param
     * @return
     * @throws ArithmeticException
     */
    @Recover
    public boolean recoverHandleBizData(Throwable e, int param) throws ArithmeticException {
        log.error(String.format("handleBizData全部重试失败，执行recoverHandleBizData,param=%d",param),e);
        return false;
    }

}
