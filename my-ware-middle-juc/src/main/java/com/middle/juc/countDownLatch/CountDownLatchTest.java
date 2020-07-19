package com.middle.juc.countDownLatch;

import com.middle.juc.config.MyTreadFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {



    public static void main(String[] args) throws Exception{
        mountain(20);
    }



    //场景一 几个人去爬山，都爬上山顶后，聚餐
    public static void mountain(int pNum) throws Exception{

        CountDownLatch countDownLatch = new CountDownLatch(pNum);
        //创建线程池
        long keepAliveTime = 20;
        TimeUnit unit = TimeUnit.SECONDS;
        MyTreadFactory myTreadFactory = new MyTreadFactory();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(20,50,keepAliveTime,unit,new LinkedBlockingDeque<Runnable>(),myTreadFactory);

        for(int i=0;i<pNum;i++){

            executor.execute(()->{
                //登山
                System.out.println("登山人----"+Thread.currentThread().getName()+"到达山顶------");
                countDownLatch.countDown();

            });
        }
        countDownLatch.await();
        System.out.println("人到齐了，开餐！！！");
        executor.shutdown();

    }





}
