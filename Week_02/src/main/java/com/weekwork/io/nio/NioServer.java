package com.weekwork.io.nio;


import com.weekwork.io.bio.BioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * nio
 */
public class NioServer {
    private static final Logger logger = LoggerFactory.getLogger(BioClient.class);

    private static int clientCount = 0;
    private static AtomicInteger counter = new AtomicInteger(0);
    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            ssc.bind(new InetSocketAddress("localhost",8081));//绑定端口
            while (true){
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if(key.isAcceptable()){
                        ServerSocketChannel ssc1 = (ServerSocketChannel) key.channel();
                        SocketChannel sc = null;
                        while ((sc  = ssc1.accept()) !=null){
                            sc.configureBlocking(false);
                            sc.register(selector,SelectionKey.OP_READ);
                            InetSocketAddress remoteSocketAddress = (InetSocketAddress) sc.socket().getRemoteSocketAddress();
                            logger.info(now() + "->" + remoteSocketAddress.getHostName() + ":" + remoteSocketAddress.getPort() + "->" + Thread.currentThread().getId() + ":" + (++clientCount));
                        }
                    }else if(key.isReadable()){
                        key.interestOps(key.interestOps() & (~ SelectionKey.OP_READ));
                        service((SocketChannel)key.channel(),key);
                    }
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

    private static void service(SocketChannel channel, SelectionKey key) {
        new Thread(()->{
            counter.getAndIncrement();
            try (SocketChannel sc = channel){
                String result = readBytes(sc);
                System.out.println(now() + "->" + result + "->" + Thread.currentThread().getId() + ":" + counter.get());
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }).start();

    }

    private static String readBytes(SocketChannel channel) throws IOException {
        long start = 0;
        int total = 0;
        int count = 0;

        ByteBuffer bb = ByteBuffer.allocate(1024);
        long begin = System.currentTimeMillis();
        while ((count = channel.read(bb))>-1){
            if(start <1){
                start = System.currentTimeMillis();
            }
            total += count;
            bb.clear();
        }
        long end = System.currentTimeMillis();
        return "wait=" + (start - begin) + "ms,read=" + (end - start) + "ms,total=" + total + "bs";
    }

    private static String now(){
        return sdf.format(new Date());
    }
}
