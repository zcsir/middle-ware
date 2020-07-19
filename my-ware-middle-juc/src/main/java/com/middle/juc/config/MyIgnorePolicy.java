package com.middle.juc.config;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
/**
 * 自定义拒绝策略
 * @author 郑晨
 * @date 2020/6/7 10:25
 * @updateUser
 * @updateDate
 * @updateRemark
 * @version 1.0
 */
public class MyIgnorePolicy implements RejectedExecutionHandler {


    @Override
    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
        doLog(runnable,threadPoolExecutor);
    }

    private void doLog(Runnable r, ThreadPoolExecutor e) {
        // 可做日志记录等
        System.err.println( r.toString() + " rejected");
//          System.out.println("completedTaskCount: " + e.getCompletedTaskCount());
    }

}
