package org.redis.manager.rediscache.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RedisCacheService {

    @Autowired
    private ReactiveStringRedisTemplate redis;

    @Autowired
    private ReactiveRedisOperations<String, String> operationsL;

    public Mono<String> getCache() {
        return operationsL
                .opsForValue()
                .get("Alvaro");
    }

    public Mono<String> createCache() {
        return operationsL
                .opsForValue()
                .set("Alvaro", "12")
                .map(aBoolean -> "");
    }

    public Mono<String> updateCache() {
        return operationsL
                .opsForValue()
                .getAndSet("Alvaro", "124")
                .map(aBoolean -> "");
    }

    public Mono<String> deleteCache() {
        return operationsL
                .opsForValue()
                .delete("Alvaro")
                .map(aBoolean -> "");
    }

    public Mono<String> getAllCache() {
        return operationsL
                .opsForValue()
                .multiGet(List.of("Alvaro","walvao"))
                .map(str -> str.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(",")));
    }

}
