package com.lanya.concurrent;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * ReentrantReadWriteLock测试
 * 参考：https://tobebetterjavaer.com/thread/suo.html#_6-%E7%8B%AC%E4%BA%AB%E9%94%81-vs-%E5%85%B1%E4%BA%AB%E9%94%81
 *
 * @author 兰崖 shenkaixing.skx
 * @date 2023/6/19 3:04 下午
 */
public class ReentrantReadWriteLockTest {

    public static void main1(String[] args) throws InterruptedException {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        WriteLock writeLock = readWriteLock.writeLock();
        //ReadLock readLock = readWriteLock.readLock();
        //condition是要和lock配合使用的也就是condition和Lock是绑定在一起的，而lock的实现原理又依赖于AQS，
        //自然而然ConditionObject就成为了AQS的一个内部类
        Condition writeCondition = writeLock.newCondition();
        //Condition readCondition = readLock.newCondition();
        //在锁机制的实现上，AQS内部维护了一个同步队列，如果是独占式锁的话，所有获取锁失败的线程的尾插入到同步队列
        //同样的，condition内部也是使用同样的方式，内部维护了一个 等待队列，所有调用condition.await方法的线程会加入到等待队列中
        //并且线程状态转换为等待状态

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                writeLock.lock();
                try {
                    //调用condition.await方法后线程依次尾插入到等待队列中，如图队列中的线程引用依次为Thread-0,Thread-1,Thread-2....Thread-8；
                    //等待队列是一个单向队列。通过我们的猜想然后进行实验验证，我们可以得出等待队列的示意图如下图所示：

                    writeCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    writeLock.unlock();
                }
            });
            thread.start();
        }
        Thread.sleep(4000);
        //同时还有一点需要注意的是：我们可以多次调用lock.newCondition()方法创建多个condition对象，
        // 也就是一个lock可以持有多个等待队列

        //总结下，就是当前线程被中断或者调用condition.signal/condition.signalAll方法当
        // 前节点移动到了同步队列后 ，这是当前线程退出await方法的前提条件。

    }


    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    private static volatile boolean flag = false;

    public static void main(String[] args) {
        //开启了两个线程waiter和signaler，waiter线程开始执行的时候由于条件不满足，
        // 执行condition.await方法使该线程进入等待状态同时释放锁，signaler线程获取到锁之后更改条件，
        // 并通知所有的等待线程后释放锁。这时，waiter线程获取到锁，
        // 并由于signaler线程更改了条件此时相对于waiter来说条件满足，继续执行。
        Thread waiter = new Thread(new waiter());
        waiter.start();
        Thread signaler = new Thread(new signaler());
        signaler.start();
    }

    static class waiter implements Runnable {

        @Override
        public void run() {
            lock.lock();
            try {
                while (!flag) {
                    System.out.println(Thread.currentThread().getName() + "当前条件不满足等待");
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + "接收到通知条件满足");
            } finally {
                lock.unlock();
            }
        }
    }

    static class signaler implements Runnable {

        @Override
        public void run() {
            lock.lock();
            try {
                flag = true;
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }
}
