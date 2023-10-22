package org.redis.manager.rediscache.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.redis.manager.rediscache.model.RedisModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.ReactiveServerCommands;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class RedisOperator {
    @Autowired
    private ReactiveStringRedisTemplate redis;

    @Autowired
    private ReactiveRedisOperations<String, String> operationsL;

    @Autowired
    private ObjectMapper convert;

    public Mono<String> getCache(String key) {
        return operationsL
                .opsForValue()
                .get(key);
    }

    public Mono<String> createCache(String key, String value) {
        return operationsL
                .opsForValue()
                .set(key, value)
                .map(aBoolean -> "");

    }

    public Mono<String> updateCache(String key, String value) {
        return operationsL
                .opsForValue()
                .getAndSet(key, value)
                .map(aBoolean -> "");
    }

    public Mono<String> deleteCache(String key) {
        return operationsL
                .opsForValue()
                .delete(key)
                .map(aBoolean -> "");
    }

    public Flux<RedisModel> getAllCacheByKeys(List<String> keys) {
        List<RedisModel> models = new ArrayList<>();
        return operationsL
                .opsForValue()
                .multiGet(keys)
                .flatMapIterable(str -> {
                    str.forEach(s -> models.add(RedisModel.builder().value(s).build()));
                    return models;
                });
    }

    public Flux<RedisModel> getAllCache(String keyPattern) {
        return operationsL
                .keys(keyPattern.concat(":*"))
                .flatMap(key -> getCache(key).map(value -> RedisModel.builder().value(value).key(key).build()));
    }

    public Flux<RedisModel> getAllKeys(String keyPattern) {
        return operationsL
                .keys(keyPattern.concat(":*"))
                .map(key -> RedisModel.builder().key(key).build());
    }

    public Mono<String> deleteAllCache() {
        return redis.getConnectionFactory().getReactiveConnection().serverCommands().flushAll();
    }

    public Mono<String> getSizeCache() {
        return redis.getConnectionFactory().getReactiveConnection().serverCommands().dbSize().map(String::valueOf);
    }
}
