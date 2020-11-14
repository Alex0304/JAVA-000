package com.weekwork.conc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Homework04 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(1);
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        final Future<Integer> task = executorService.submit(() -> sum());

        int result = task.get(); //这是得到的返回值
        executorService.shutdown();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }

}
