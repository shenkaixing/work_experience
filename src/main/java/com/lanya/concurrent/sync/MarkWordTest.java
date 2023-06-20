package com.lanya.concurrent.sync;

import lombok.extern.slf4j.Slf4j;
import org.openjdk.jol.info.ClassLayout;

/**
 * MarkWord 测试
 * 查看对象内存布局的工具 JOL (java object layout)
 *
 * https://tobebetterjavaer.com/thread/pianxiangsuo.html#%E5%9C%BA%E6%99%AF3
 * https://tobebetterjavaer.com/thread/pianxiangsuo.html#%E6%80%BB%E7%BB%93
 *
 * @author 兰崖 shenkaixing.skx
 * @date 2023/6/19 5:06 下午
 */
@Slf4j
public class MarkWordTest {

    /**
     * 这样的结果是符合我们预期的，但是结果中的 biasable 状态，在 MarkWord 表格中并不存在，其实这是一种匿名偏向状态，是对象初始化中，JVM 帮我们做的
     *
     * 这样当有线程进入同步块：
     *
     * 可偏向状态：直接就 CAS 替换 ThreadID，如果成功，就可以获取偏向锁了
     * 不可偏向状态：就会变成轻量级锁
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main1(String[] args) throws InterruptedException {
        // 睡眠 5s
        Thread.sleep(5000);
        Object o = new Object();
        log.info("未进入同步块，MarkWord 为：");
        log.info(ClassLayout.parseInstance(o).toPrintable());
        synchronized (o) {
            log.info(("进入同步块，MarkWord 为："));
            log.info(ClassLayout.parseInstance(o).toPrintable());
        }
    }

    /**
     * 标记1: 初始可偏向状态
     * 标记2：偏向主线程后，主线程退出同步代码块
     * 标记3: 新线程进入同步代码块，升级成了轻量级锁
     * 标记4: 新线程轻量级锁退出同步代码块，主线程查看，变为不可偏向状态
     * 标记5: 由于对象不可偏向，同场景1主线程再次进入同步块，自然就会用轻量级锁
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main2(String[] args) throws InterruptedException {
        // 睡眠 5s
        Thread.sleep(5000);
        Object o = new Object();
        log.info("未进入同步块，MarkWord 为：");
        log.info(ClassLayout.parseInstance(o).toPrintable());
        synchronized (o) {
            log.info(("进入同步块，MarkWord 为："));
            log.info(ClassLayout.parseInstance(o).toPrintable());
        }

        Thread t2 = new Thread(() -> {
            synchronized (o) {
                log.info("新线程获取锁，MarkWord为：");
                log.info(ClassLayout.parseInstance(o).toPrintable());
            }
        });

        t2.start();
        t2.join();
        log.info("主线程再次查看锁对象，MarkWord为：");
        log.info(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o) {
            log.info(("主线程再次进入同步块，MarkWord 为："));
            log.info(ClassLayout.parseInstance(o).toPrintable());
        }
    }

    /**
     * hashcode 不是创建对象就帮我们写到对象头中的，而是要经过第一次调用 Object::hashCode()
     * 或者System::identityHashCode(Object) 才会存储在对象头中的。第一次生成的 hashcode后，
     * 该值应该是一直保持不变的
     *
     * 偏向锁又是来回更改锁对象的 markword，必定会对 hashcode 的生成有影响，那怎么办呢？
     *
     * 结论就是：即便初始化为可偏向状态的对象，一旦调用 Object::hashCode()
     * 或者System::identityHashCode(Object) ，进入同步块就会直接使用轻量级锁
     *
     * #
     */
    public static void main3(String[] args) throws InterruptedException {
        // 睡眠 5s
        Thread.sleep(5000);

        Object o = new Object();
        log.info("未生成 hashcode，MarkWord 为：");
        log.info(ClassLayout.parseInstance(o).toPrintable());

        o.hashCode();
        log.info("已生成 hashcode，MarkWord 为：");
        log.info(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o) {
            log.info(("进入同步块，MarkWord 为："));
            log.info(ClassLayout.parseInstance(o).toPrintable());
        }
    }

    /**
     * 那假如对象处于已偏向状态，在同步块中调用了那两个方法会发生什么呢？继续代码验证：
     *
     * 结论就是：如果对象处在已偏向状态，生成 hashcode 后，就会直接升级成重量级锁
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main4(String[] args) throws InterruptedException {
        // 睡眠 5s
        Thread.sleep(5000);

        Object o = new Object();
        log.info("未生成 hashcode，MarkWord 为：");
        log.info(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o) {
            log.info(("进入同步块，MarkWord 为："));
            log.info(ClassLayout.parseInstance(o).toPrintable());
            o.hashCode();
            log.info("已偏向状态下，生成 hashcode，MarkWord 为：");
            log.info(ClassLayout.parseInstance(o).toPrintable());
        }
    }

    /**
     * 调用 Object.wait() 方法会发生什么？
     * Object 除了提供了上述 hashcode 方法，还有 wait() 方法，这也是我们在同步块中常用的，那这会对锁产生哪些影响呢？来看代码：
     *
     * 结论就是，wait 方法是互斥量（重量级锁）独有的，一旦调用该方法，就会升级成重量级锁（这个是面试可以说出的亮点内容哦）
     */

    public static void main(String[] args) throws InterruptedException {
        // 睡眠 5s
        Thread.sleep(5000);

        Object o = new Object();
        log.info("未生成 hashcode，MarkWord 为：");
        log.info(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o) {
            log.info(("进入同步块，MarkWord 为："));
            log.info(ClassLayout.parseInstance(o).toPrintable());

            log.info("wait 2s");
            o.wait(2000);

            log.info(("调用 wait 后，MarkWord 为："));
            log.info(ClassLayout.parseInstance(o).toPrintable());
        }
    }

}
