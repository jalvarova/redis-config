package org.redis.manager.rediscache.service;

import lombok.extern.slf4j.Slf4j;
import org.redis.manager.rediscache.exception.NotFoundException;
import org.redis.manager.rediscache.model.Decision;
import org.redis.manager.rediscache.model.RedisModel;
import org.redis.manager.rediscache.redis.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;

@Service
@Slf4j
public class RedisCacheService {

    private static final String PARAMETER_KEY = "key";
    private static final String PARAMETER_KEY_PATTERN = "keyPattern";

    private static final String PARAMETER_DELETE_ALL = "deleteAll";

    @Autowired
    private RedisOperator redisOperator;

    public Mono<String> getCache(ServerRequest request) {
        String keyPattern = request.queryParam(PARAMETER_KEY_PATTERN).get();
        String key = request.queryParam(PARAMETER_KEY).get();
        String keyCache = keyPattern.concat(":").concat(key);
        return redisOperator.getCache(keyCache)
                .switchIfEmpty(Mono.error(() -> new NotFoundException(keyCache)));
    }

    public Mono<String> createCache(ServerRequest request) {
        return request
                .bodyToMono(RedisModel.class)
                .flatMap(model ->
                        redisOperator.createCache(model.getKey().concat(":").concat(UUID.randomUUID().toString()), model.getValue())
                );

    }

    public Mono<String> updateCache(ServerRequest request) {
        return request
                .bodyToMono(RedisModel.class)
                .flatMap(model -> {
                            String keyCache = model.getKeyPattern().concat(":").concat(model.getKey());
                            return redisOperator.updateCache(keyCache, model.getValue())
                                    .switchIfEmpty(Mono.error(() -> new NotFoundException(keyCache)));
                        }
                );
    }

    public Mono<String> deleteCache(ServerRequest request) {
        String keyPattern = request.pathVariable(PARAMETER_KEY_PATTERN);
        String key = request.pathVariable(PARAMETER_KEY);
        String keyCache = keyPattern.concat(":").concat(key);
        return redisOperator.deleteCache(keyCache)
                .switchIfEmpty(Mono.error(() -> new NotFoundException(keyCache)));
    }

    public Flux<RedisModel> getAllCache(ServerRequest request) {
        String key = request.pathVariable(PARAMETER_KEY);
        return redisOperator.getAllCache(key.concat(":*"));
    }

    public Flux<RedisModel> getAllKeys(ServerRequest request) {
        String key = request.pathVariable(PARAMETER_KEY);
        return redisOperator.getAllKeys(key.concat(":*"));
    }

    public Flux<RedisModel> getAllByKeys(ServerRequest request) {
        return request
                .bodyToMono(String[].class)
                .flux()
                .flatMap(model -> redisOperator.getAllCacheByKeys(Arrays.asList(model)));
    }

    public Mono<String> deleteAllCache(ServerRequest request) {
        Decision decision = Decision.valueOf(request.queryParam(PARAMETER_DELETE_ALL).get());
        return redisOperator.deleteAllCache(decision);
    }

    public Mono<String> getSizeCache(ServerRequest request) {
        return redisOperator.getSizeCache();
    }

    public Mono<Properties> getPropertiesCache(ServerRequest request) {
        return redisOperator.getInfoCache();
    }

    public Mono<String> getTimeCache(ServerRequest request) {
        return redisOperator.getTimeCache();
    }

    public Flux<RedisClientInfo> getClientCache(ServerRequest request) {
        return redisOperator.getClientCache();
    }

}
