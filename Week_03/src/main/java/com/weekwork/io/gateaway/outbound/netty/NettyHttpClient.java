package com.weekwork.io.gateaway.outbound.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.spel.ast.NullLiteral;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class NettyHttpClient {
    private static final Logger logger = LoggerFactory.getLogger(NettyHttpOutboundhandler.class);

    private String host;

  //  private Channel clientChannel;


    private EventLoopGroup group = new NioEventLoopGroup();
    private int port;

    public NettyHttpClient(String url, int port) {
        this.host = url;
        this.port = port;
    }

    public void connect(FullHttpRequest fullRequest) throws InterruptedException {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new HttpResponseDecoder());
                    socketChannel.pipeline().addLast(new HttpRequestEncoder());
                    socketChannel.pipeline().addLast(new NettyHttpOutboundhandler());
                }
            });
            //发起同步连接操作
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();

            /*//注册连接事件
            channelFuture.addListener((ChannelFutureListener)future -> {
                //如果连接成功
                if (future.isSuccess()) {
                    logger.info("客户端[" + channelFuture.channel().localAddress().toString() + "]已连接...");
                    clientChannel = channelFuture.channel();
                }
                //如果连接失败，尝试重新连接
                else{
                    logger.info("客户端[" + channelFuture.channel().localAddress().toString() + "]连接失败，重新连接中...");
                    future.channel().close();
                    bootstrap.connect(host, port);
                }
            });

            //注册关闭事件
            channelFuture.channel().closeFuture().addListener(cfl -> {
                close();
                logger.info("客户端[" + channelFuture.channel().localAddress().toString() + "]已断开...");
            });*/

            DefaultFullHttpRequest request = new DefaultFullHttpRequest(HTTP_1_1, HttpMethod.GET,
                    fullRequest.uri(), Unpooled.wrappedBuffer("i m a demo".getBytes()));
            channelFuture.channel().write(request);
            channelFuture.channel().flush();
            channelFuture.channel().closeFuture().sync();

           /* f.channel().write(request);
            f.channel().flush();
            f.channel().closeFuture().sync();*/
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

   /* private void close() {
        //关闭客户端套接字
        if(clientChannel!=null){
            clientChannel.close();
        }
        //关闭客户端线程组
        if (group != null) {
            group.shutdownGracefully();
        }
    }


    *//**
     * 客户端发送消息
     * @param message
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     *//*
    public Object send(Object message) throws InterruptedException, ExecutionException {
        ChannelPromise promise = handler.sendMessage(message);
        promise.await(3, TimeUnit.SECONDS);
        return handler.getResponse();
    }*/
}
