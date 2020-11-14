package com.weekwork.conc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Homework08 {

    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        new Thread(()->FiboResult.setResult()).start();
        TimeUnit.MILLISECONDS.sleep(10);
        int result = FiboResult.getResult(); //这是得到的返回值
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

    static class FiboResult{
        private static int result;

        public static synchronized int getResult(){
            return result;
        }

        public static synchronized void setResult(){
            result = sum();
        }
    }
}
