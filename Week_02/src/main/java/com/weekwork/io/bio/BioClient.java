package com.weekwork.io.bio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BioClient {
    private static final Logger logger = LoggerFactory.getLogger(BioClient.class);
    
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        try {
            for(int i = 0;i<20 ; i++){
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress("localhost",8080));
                request(socket,i);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }

    }

    private static void request(Socket socket, int i) {
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(RANDOM.nextInt(6)+5);
                socket.getOutputStream().write(prepareBytes());
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }
        }).start();
    }

    private static byte[] prepareBytes() {
        byte[] bytes = new byte[1024 * 1024];
        for(int i =0;i<bytes.length;i++){
            bytes[i] = 1;
        }
        return bytes;
    }

}
