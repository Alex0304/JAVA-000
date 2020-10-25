package com.weekwork.nio;

public class NeetyServerApplication {
    public static void main(String[] args) {
        final HttpServer httpServer = new HttpServer(8082,false);
        try {
            httpServer.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
