package com.weekwork.io.gateaway.rounter;

import cn.hutool.core.util.StrUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class Loadbalancer {

    private static final String PATH = "D:\\code\\JAVA-000\\target\\classes\\server.properties";

    private List<String> servers;
    private HttpEndpointRouter httpEndpointRouter;

    public Loadbalancer(HttpEndpointRouter httpEndpointRouter) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(PATH);
        Properties properties = new Properties();
        properties.load(fileInputStream);
        String servers = properties.getProperty("server");
        this.servers = StrUtil.isEmptyIfStr(servers)?null: Arrays.asList(servers.split(","));
        this.httpEndpointRouter = httpEndpointRouter;
    }

    public String loadbalance() {
        return httpEndpointRouter.route(this.servers);
    }

    public static void main(String[] args) throws IOException {
        Loadbalancer loadbalancer = new Loadbalancer(new HttpEndpointRouterImpl());
        System.out.println(loadbalancer.loadbalance());
    }
}
