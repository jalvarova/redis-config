package org.redis.manager.rediscache.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.redis.manager.rediscache.model.RedisModel;
import org.redis.manager.rediscache.redis.RedisOperator;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CacheConsumer {

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private ObjectMapper convert;

    @RabbitListener(queues = {"${spring.rabbitmq.queue.name}"})
    void consumeMessageCache(String body) throws JsonProcessingException {
        log.info("Receive message " + body);
        RedisModel redisModel = convert.readValue(body, RedisModel.class);
        redisOperator.getSizeCache().map(s -> {log.info("Size cache "+ s); return s;}).block();
        redisOperator.createCache(redisModel.getKey(), redisModel.getValue()).block();
    }
}
