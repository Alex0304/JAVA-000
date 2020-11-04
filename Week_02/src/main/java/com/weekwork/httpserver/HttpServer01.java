package com.weekwork.httpserver;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * BIO 阻塞式 socket,单线程
 */
public class HttpServer01 {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);//绑定8080端口
        while (true) {
            Socket socket = serverSocket.accept();//接受客户端的连接
            service(socket);//接受到客户端的连接，开始处理客户端的请求，业务处理，返回响应数据
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
