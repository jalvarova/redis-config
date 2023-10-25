package org.redis.manager.rediscache.redis;

import lombok.extern.slf4j.Slf4j;
import org.redis.manager.rediscache.model.RedisModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.redis.manager.rediscache.util.CacheUtil.generateRandomLetters;

@Component
@Slf4j
public class RedisOperator {
    @Autowired
    private ReactiveStringRedisTemplate redis;

    @Autowired
    private ReactiveRedisOperations<String, String> operationsL;

    public Mono<String> getCache(String key) {
        return operationsL
                .opsForValue()
                .get(key);
    }

    public Mono<String> createCache(String key, String value) {
        return operationsL
                .opsForValue()
                .set(key, generateRandomLetters())
                .map(aBoolean -> "");

    }

    public Mono<String> updateCache(String key, String value) {
        return operationsL
                .opsForValue()
                .get(key)
                .switchIfEmpty(Mono.empty())
                .flatMap(s -> operationsL
                        .opsForValue().set(key, value)).map(aBoolean -> value);

    }

    public Mono<String> deleteCache(String key) {
        return operationsL
                .opsForValue()
                .delete(key)
                .flatMap(aBoolean -> aBoolean ? Mono.just(key) : Mono.empty());
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
                .keys(keyPattern)
                .flatMap(key -> getCache(key).map(value -> RedisModel.builder().value(value).key(key).build()));
    }

    public Flux<RedisModel> getAllKeys(String keyPattern) {
        return operationsL
                .keys(keyPattern)
                .map(key -> RedisModel.builder().key(key).build());
    }

    public Mono<String> deleteAllCache() {
        return redis.getConnectionFactory().getReactiveConnection().serverCommands().flushAll();
    }

    public Mono<String> getSizeCache() {
        return redis.getConnectionFactory().getReactiveConnection().serverCommands().dbSize().map(String::valueOf);
    }

    public Mono<Properties> getInfoCache() {
        return redis.getConnectionFactory().getReactiveConnection().serverCommands().info();
    }

    public Mono<String> getTimeCache() {
        return redis.getConnectionFactory().getReactiveConnection().serverCommands().time(TimeUnit.MILLISECONDS)
                .map(aLong -> (aLong / 1000) % 60)
                .map(String::valueOf);
    }

    public Flux<RedisClientInfo> getClientCache() {
        return redis.getConnectionFactory().getReactiveConnection().serverCommands().getClientList();
    }
}
