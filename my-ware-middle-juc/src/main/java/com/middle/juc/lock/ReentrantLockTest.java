package com.middle.juc.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    public static int count = 0;
    public static AtomicInteger count1 = new AtomicInteger(0);
    public static volatile int threadTotal = 5000;

    public static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws Exception {

       // increaseNum();
        increaseNum1();
    }


    //场景 对数字进行增加操作，最后查看数字的大小

    public static void increaseNum() throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();

        CountDownLatch countDownLatch = new CountDownLatch(threadTotal);

        for (int i = 0; i < threadTotal; i++) {
            executorService.submit(() -> {
                //increase();
                //increase1();
                countDownLatch.countDown();
            });

        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("cout-----" + count);

    }

    public static void increaseNum1() throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();

        CountDownLatch countDownLatch = new CountDownLatch(threadTotal);

        for (int i = 0; i < threadTotal; i++) {
            executorService.submit(() -> {
                increase2();
                countDownLatch.countDown();
            });

        }
        countDownLatch.await();
        executorService.shutdown();
        System.out.println("cout1-----" + count1);

    }



    //lock 加锁
    private static void increase() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    private synchronized static void increase1() {
        count++;
    }

    private  static void increase2() {
        count1.incrementAndGet();
    }
}
