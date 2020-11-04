package com.weekwork.httpserver;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程作为一种资源，不能根据请求无限创建销毁，使得系统性能下降，并发能力下降
 * 故由无限线程池改为固定的线程，控制线程池在一定数量，使得线程得以复用
 */
public class HttpServer03 {

    public static void main(String[] args) throws IOException {
        final ExecutorService executorService = Executors.newFixedThreadPool(40);//创建的线程的数量需要根据线程池需要处理的具体的业务有关，IO密集型与计算密集型
        final ServerSocket serverSocket = new ServerSocket(8082);
        while (true){
            final Socket socket = serverSocket.accept();
            executorService.submit(()->service(socket));
        }
    }

    private static void service(Socket socket) {
        try (OutputStream outputStream = socket.getOutputStream();
             PrintWriter printWriter = new PrintWriter(outputStream)
        ) {
            TimeUnit.MILLISECONDS.sleep(20);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            printWriter.println();
            printWriter.write("hello,netty");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
