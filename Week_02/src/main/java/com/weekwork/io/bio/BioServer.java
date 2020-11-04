package com.weekwork.io.bio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;



/**
 * 同步阻塞式server
 */
public class BioServer {
    private static final Logger logger = LoggerFactory.getLogger(BioServer.class);

    private static AtomicInteger counter = new AtomicInteger(0);

    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("localhost",8080));
            while (true){
                Socket  socket = serverSocket.accept();
                service(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void service(Socket socket) {
        new Thread(()->{
            InetSocketAddress remoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
            logger.info(now() + "->" + remoteSocketAddress.getHostName() + ":" + remoteSocketAddress.getPort() + "->" + Thread.currentThread().getId() + ":" + counter.incrementAndGet());
            try(Socket s = socket) {
                String result = readBytes(s.getInputStream());
                logger.info(now() + "->" + result + "->" + Thread.currentThread().getId() + ":" + counter.getAndDecrement());
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }).start();
    }

    private static String readBytes(InputStream is) throws IOException {
        long start  = 0;
        int count = 0;
        int total = 0;
        byte[] bytes = new byte[1024];
        long begin = System.currentTimeMillis();
        while ((count = is.read(bytes))>-1){
            if(start<1){
                start = System.currentTimeMillis();
            }
            total += count;
        }
        long end = System.currentTimeMillis();
        return "wait=" + (start - begin) + "ms,read=" + (end - start) + "ms,total=" + total + "bs";
    }

    private static String now(){
        return sdf.format(new Date());
    }
}
