package com.lanya.concurrent;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 覆盖同步
 *
 * AQS是AbstractQueuedSynchronizer的简称，即抽象队列同步器，从字面意思上理解:
 *
 * 抽象：抽象类，只实现一些主要逻辑，有些方法由子类实现；
 * 队列：使用先进先出（FIFO）队列存储数据；
 * 同步：实现了同步的功能。
 *
 * @author 兰崖 shenkaixing.skx
 * @date 2023/6/14 3:43 下午
 */
public class MyQueuedSynchronizer extends AbstractQueuedSynchronizer {

    @Override
    public boolean tryRelease(int arg) {
        return false;
    }

}
