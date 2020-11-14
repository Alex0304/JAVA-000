package com.weekwork.io.gateaway.util;


import cn.hutool.core.map.MapUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public class HttpClientUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static final String REMOTE_HOST = "http://127.0.0.1:8081";

    public static String get(String uri, Map<String,String> headers,String server) throws IOException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("http://"+server+uri);
        Optional.ofNullable(headers).orElse(MapUtil.newHashMap()).entrySet().stream().forEach(e -> {
            get.setHeader(e.getKey(),e.getValue());
        });
        HttpResponse httpResponse = client.execute(get);
        StringBuffer stringBuffer = new StringBuffer();
        try (InputStream io = httpResponse.getEntity().getContent()){
            byte[] bytes = new byte[1024];
            int i;
            while ((i=io.read(bytes))!=-1){
                stringBuffer.append(new String(bytes, 0, i));
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return stringBuffer.toString();
    }

    public static String get(String uri) throws IOException {
        return get(uri,null,REMOTE_HOST);
    }
}
