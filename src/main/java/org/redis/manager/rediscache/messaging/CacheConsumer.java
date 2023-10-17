package org.redis.manager.rediscache.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CacheConsumer {

    @RabbitListener(queues = {"${spring.rabbitmq.queue.name}"})
    void consumeMessageCache(String body) {

        log.info("Receive message" + body);
    }
}
