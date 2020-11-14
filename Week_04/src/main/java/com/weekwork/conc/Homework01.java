package com.weekwork.conc;


import java.util.concurrent.CountDownLatch;

/**
 * 使用countdownlatch
 */
public class Homework01 {

    public static void main(String[] args) {

        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final ResultRunable resultRunable = new ResultRunable(countDownLatch);
        new Thread(resultRunable).start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+resultRunable.result);

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

    static class ResultRunable implements Runnable{
        private int result;
        private CountDownLatch countDownLatch;

        public ResultRunable(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            result= sum();
            countDownLatch.countDown();
        }


    }
}
