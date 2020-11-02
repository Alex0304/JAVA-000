package com.weekwork.io.gateaway.rounter;

import java.util.List;

public interface HttpEndpointRounter {
    String route(List<String> endpoints);

}
