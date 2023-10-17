package org.redis.manager.rediscache.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CachePublish {

    @Autowired
    private RabbitTemplate broker;

    public Mono<String> sendMessage(String value) {
        broker.convertAndSend("example", "#", value);
        return Mono.just("publish");
    }
}
