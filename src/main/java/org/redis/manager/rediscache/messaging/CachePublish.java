package org.redis.manager.rediscache.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CachePublish {

    @Value(value = "${spring.rabbitmq.exchange}")
    private String exchange;

    @Value(value = "${spring.rabbitmq.routing-key}")
    private String routingKey;

    @Autowired
    private RabbitTemplate broker;

    public Mono<String> sendMessage(String value) {
        broker.convertAndSend(exchange, routingKey, value);
        return Mono.just("publish");
    }
}
