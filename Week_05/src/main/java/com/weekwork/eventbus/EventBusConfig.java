package com.weekwork.eventbus;


import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventBusConfig {

    @Bean
    public EventBus productEventBus(ProductEventListener productEventListener){
        EventBus eventBus = new EventBus("PRODUCT_EVENT_BUS");
        eventBus.register(productEventListener);
        return eventBus;
    }
}
