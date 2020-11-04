package com.weekwork.io.gateaway.inbound;

import cn.hutool.core.map.MapUtil;
import com.weekwork.io.gateaway.filter.HttpRequestFilter;
import com.weekwork.io.gateaway.filter.HttpRequestFilterImpl;
import com.weekwork.io.gateaway.outbound.httpclient.HttpOutboundHandler;
import com.weekwork.io.gateaway.outbound.netty.NettyHttpClient;
import com.weekwork.io.gateaway.rounter.HttpEndpointRouter;
import com.weekwork.io.gateaway.rounter.HttpEndpointRouterImpl;
import com.weekwork.io.gateaway.rounter.Loadbalancer;
import com.weekwork.io.gateaway.util.HttpClientUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.handler.codec.rtsp.RtspHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.rtsp.RtspResponseStatuses.OK;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private HttpRequestFilter filter;

    private Loadbalancer loadbalancer;


    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final String proxyServer;
    private HttpOutboundHandler handler;

    public HttpInboundHandler(String proxyServer) throws IOException {
        this.proxyServer = proxyServer;
        //handler = new HttpOutboundHandler(this.proxyServer);
        this.filter = new HttpRequestFilterImpl();
        this.loadbalancer = new Loadbalancer(new HttpEndpointRouterImpl());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            //remoteHandlerTest(fullRequest,ctx);
           /* NettyHttpClient nettyHttpClient = new NettyHttpClient("127.0.0.1",8080);
            nettyHttpClient.connect(fullRequest);*/
            filterTest(fullRequest,ctx);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private void filterTest(FullHttpRequest fullRequest, ChannelHandlerContext ctx){
        filter.filter(fullRequest,ctx);//请求过滤器链
        //从后端服务中选择一个可用的服务
        String server = loadbalancer.loadbalance();
        remoteHandlerTest(fullRequest,ctx, MapUtil.of("nio",fullRequest.headers().get("nio")),server);
    }

    private void remoteHandlerTest(FullHttpRequest fullRequest, ChannelHandlerContext ctx, Map<String,String> headers,String server) {
        FullHttpResponse response = null;
        try {
            String result = HttpClientUtil.get(fullRequest.uri(),headers,server);
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(result.getBytes("UTF-8")));
            response.headers().set("Content-Type", "application/json");
        } catch (Exception e) {
            logger.error("处理测试接口出错", e);
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
        }
        response.headers().setInt("Content-Length", response.content().readableBytes());
    }


    private void remoteHandlerTest(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        remoteHandlerTest(fullRequest,ctx,null,"127.0.0.1:8080");
    }




}
