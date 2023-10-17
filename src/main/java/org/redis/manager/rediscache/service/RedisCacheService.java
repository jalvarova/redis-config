package org.redis.manager.rediscache.service;

import lombok.extern.slf4j.Slf4j;
import org.redis.manager.rediscache.model.RedisModel;
import org.redis.manager.rediscache.redis.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class RedisCacheService {

    @Autowired
    private RedisOperator redisOperator;

    public Mono<String> getCache(ServerRequest request) {
        String key = request.pathVariable("key");
        return redisOperator.getCache(key);
    }

    public Mono<String> createCache(ServerRequest request) {
        return request.bodyToMono(RedisModel.class)
                .flatMap(model -> redisOperator.createCache(model.getKey(), model.getValue()));

    }


    public Mono<String> updateCache(ServerRequest request) {
        return request.bodyToMono(RedisModel.class)
                .flatMap(model -> redisOperator.updateCache(model.getKey(), model.getValue()));
    }

    public Mono<String> deleteCache(ServerRequest request) {
        String key = request.pathVariable("key");
        return redisOperator.deleteCache(key);
    }

    public Flux<RedisModel> getAllCache(ServerRequest request) {
        return redisOperator.getAllCache();
    }

    public Flux<RedisModel> getAllKeys(ServerRequest request) {
        return redisOperator.getAllKeys();
    }

    public Flux<RedisModel> getAllByKeys(ServerRequest request) {
        return request.bodyToMono(String[].class)
                .flux()
                .flatMap(model -> redisOperator.getAllCacheByKeys(Arrays.asList(model)));
    }
}
