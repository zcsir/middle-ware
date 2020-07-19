package com.middle.juc.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class ExecutorExample {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        List<Future<Integer>> resultList = new ArrayList<Future<Integer>>();
        Random random = new Random();
        for (int i = 0; i < 10; i ++) {
            int rand = random.nextInt(10);

            FactorialCalculator factorialCalculator = new FactorialCalculator(rand);
            Future<Integer> res = executor.submit(factorialCalculator);//异步提交, non blocking.
            resultList.add(res);
        }

        // in loop check out the result is finished
        do {
//            System.out.printf("number of completed tasks: %d\n", executor.getCompletedTaskCount());
            for (int i = 0; i < resultList.size(); i++) {
                Future<Integer> result = resultList.get(i);
                System.out.printf("Task %d : %s \n", i, result.isDone());
            }
            try {
                TimeUnit.MILLISECONDS.sleep(50);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (executor.getCompletedTaskCount() < resultList.size());


        System.out.println("Results as folloers:");
        for (int i = 0; i < resultList.size(); i++) {
            Future<Integer> result = resultList.get(i);
            Integer number = null;

            try {
                number = result.get();// blocking method
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.printf("task: %d, result %d:\n", i, number);
        }
        executor.shutdown();
    }
}
