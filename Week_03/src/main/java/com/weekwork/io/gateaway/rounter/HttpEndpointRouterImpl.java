package com.weekwork.io.gateaway.rounter;


import java.util.List;
import java.util.Random;

public class HttpEndpointRouterImpl implements HttpEndpointRouter {
    private Random random = new Random();

    @Override
    public String route(List<String> endpoints) {
        int index = random.nextInt(endpoints.size());
        return endpoints.get(index);
    }

}
