package com.weekwork.io.gateaway.outbound.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class NettyHttpClient {
    private static final Logger logger = LoggerFactory.getLogger(NettyHttpOutboundhandler.class);


    //  private Channel clientChannel;
    private NettyHttpOutboundhandler outboundHandler = new NettyHttpOutboundhandler();

    private EventLoopGroup group = new NioEventLoopGroup();

    public void connect(String url, int port, FullHttpRequest request) throws InterruptedException, UnsupportedEncodingException {
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
                    socketChannel.pipeline().addLast(outboundHandler);
                }
            });
            //发起同步连接操作
            ChannelFuture f = bootstrap.connect(url, port).sync();
            f.channel().write(request);
            f.channel().flush();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
