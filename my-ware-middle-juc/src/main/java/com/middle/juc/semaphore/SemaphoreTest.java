package com.middle.juc.semaphore;

import com.middle.juc.config.MyTreadFactory;

import java.util.concurrent.*;

public class SemaphoreTest {

    public static void main(String[] args) throws Exception {
        parking(20, 10);
    }


    //场景：停车场有有限的停车位，当停车位满了后，后面的车辆将等待

    public static void parking(int carNum, int positionNum) throws Exception {
        //创建线程池
        long keepAliveTime = 20;
        TimeUnit unit = TimeUnit.SECONDS;
        MyTreadFactory myTreadFactory = new MyTreadFactory();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 50, keepAliveTime, unit, new LinkedBlockingDeque<Runnable>(), myTreadFactory);

        //创建信号量
        Semaphore semaphore = new Semaphore(positionNum);

        for (int i = 0; i < carNum; i++) {
            executor.execute(() -> {
                //获取进入
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "---车辆进入停车场");
                    Thread.sleep(10000);
                    System.out.println(Thread.currentThread().getName() + "---车辆出停车场");
                    semaphore.release();
                    System.out.println("当前停车场剩余空位置----" + semaphore.availablePermits());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            });
        }
        executor.shutdown();

    }


}
