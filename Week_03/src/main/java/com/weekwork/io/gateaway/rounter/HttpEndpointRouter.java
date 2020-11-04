package com.weekwork.io.gateaway.rounter;

import java.util.List;

public interface HttpEndpointRouter {
    String route(List<String> endpoints);

}
