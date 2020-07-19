package com.middle.juc.cyclicBarrier;

import java.util.concurrent.*;

public class CyclicBarrierTest {


    public static void main(String[] args) {
        race(200);
    }


    //跑步比赛  当所有人站在起点线上后，才可以命令开始
    public static void race(int personNum) {

        ExecutorService executorService = Executors.newFixedThreadPool(personNum);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(personNum);
        System.out.println("比赛即将开始，运动员请上赛道！！！！");
        for (int i=0;i<personNum;i++){

            executorService.submit(()->{

                try {
                    System.out.println(Thread.currentThread().getName()+"-----准备好");
                    cyclicBarrier.await();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"----开始跑");
            });
        }
        executorService.shutdown();

    }

}
