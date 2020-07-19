package com.middle.juc.config;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MyTreadFactory  implements ThreadFactory {


    private final AtomicInteger mThreadNum = new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable,"my-thread-" + mThreadNum.getAndIncrement());
        System.out.println("MyTreadFactory--创建了线程 【"+thread.getName()+"】");
        return thread;
    }
}
