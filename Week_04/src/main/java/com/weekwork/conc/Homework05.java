package com.weekwork.conc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Homework05 {

    public static void main(String[] args) throws InterruptedException {
        final ExecutorService executorService = Executors.newFixedThreadPool(1);
        final ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        executorService.submit(new FiboTask(queue));
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        int result = queue.take(); //这是得到的返回值
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
    static class FiboTask implements Runnable{
        private ArrayBlockingQueue blockingDeque;

        public FiboTask(ArrayBlockingQueue blockingDeque) {
            this.blockingDeque = blockingDeque;
        }

        @Override
        public void run() {
            blockingDeque.offer(sum());
        }
    }

}
