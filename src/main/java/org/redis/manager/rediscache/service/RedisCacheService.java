package org.redis.manager.rediscache.service;

import lombok.extern.slf4j.Slf4j;
import org.redis.manager.rediscache.model.RedisModel;
import org.redis.manager.rediscache.redis.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Service
@Slf4j
public class RedisCacheService {

    private static final String PARAMETER_KEY = "key";
    @Autowired
    private RedisOperator redisOperator;

    public Mono<String> getCache(ServerRequest request) {
        String key = request.pathVariable(PARAMETER_KEY);
        return redisOperator.getCache(key);
    }

    public Mono<String> createCache(ServerRequest request) {
        return request.bodyToMono(RedisModel.class).flatMap(model -> redisOperator.createCache(model.getKey(), model.getValue()));

    }


    public Mono<String> updateCache(ServerRequest request) {
        return request.bodyToMono(RedisModel.class).flatMap(model -> redisOperator.updateCache(model.getKey(), model.getValue()));
    }

    public Mono<String> deleteCache(ServerRequest request) {
        String key = request.pathVariable(PARAMETER_KEY);
        return redisOperator.deleteCache(key);
    }

    public Flux<RedisModel> getAllCache(ServerRequest request) {
        String key = request.pathVariable(PARAMETER_KEY);
        return redisOperator.getAllCache(key);
    }

    public Flux<RedisModel> getAllKeys(ServerRequest request) {
        String key = request.pathVariable(PARAMETER_KEY);
        return redisOperator.getAllKeys(key);
    }

    public Flux<RedisModel> getAllByKeys(ServerRequest request) {
        return request.bodyToMono(String[].class).flux().flatMap(model -> redisOperator.getAllCacheByKeys(Arrays.asList(model)));
    }

    public Mono<String> deleteAllCache(ServerRequest request) {
        return redisOperator.deleteAllCache();
    }

    public Mono<String> getSizeCache(ServerRequest request) {
        return redisOperator.getSizeCache();
    }
}
