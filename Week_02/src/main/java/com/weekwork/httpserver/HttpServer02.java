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
 * 为提高服务器的并发能力
 * 单线程--处理请求交由线程池去处理,来一个请求就开启一个线程去处理业务
 */
public class HttpServer02 {


    public static void main(String[] args) throws IOException {
        final ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(8081);
        while (true){
            Socket socket = serverSocket.accept();
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
