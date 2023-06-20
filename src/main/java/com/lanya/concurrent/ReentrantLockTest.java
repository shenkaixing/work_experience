package com.lanya.concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock测试
 * https://tobebetterjavaer.com/thread/reentrantLock.html
 * @author 兰崖 shenkaixing.skx
 * @date 2023/6/19 10:25 上午
 */
public class ReentrantLockTest {

    /**
     * ReentrantLock重入锁，是实现Lock接口的一个类，也是在实际编程中使用频率很高的一个锁，支持重入性，
     * 表示能够对共享资源能够重复加锁，即当前线程获取该锁再次获取不会被阻塞。
     * @param args
     */
    public static void main(String[] args) {
        final Lock listLock = new ReentrantLock();
        List<String> list = new LinkedList<>();
        Thread a = new Thread(() -> {
            listLock.lock();
            try {
                list.add("hello world");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                listLock.unlock();
            }
        });

        Thread b = new Thread(() -> {
            listLock.lock();
            try {
                list.add("hello world");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                listLock.unlock();
            }
        });

        a.start();
        b.start();
    }

}
