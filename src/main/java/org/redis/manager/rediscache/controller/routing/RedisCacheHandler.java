package org.redis.manager.rediscache.controller.routing;

import lombok.RequiredArgsConstructor;
import org.redis.manager.rediscache.model.RedisModel;
import org.redis.manager.rediscache.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class RedisCacheHandler {

    @Autowired
    private RedisCacheService redisCacheService;

    public Mono<ServerResponse> getCache(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(redisCacheService.getCache(request), String.class);
    }

    public Mono<ServerResponse> createCache(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(redisCacheService.createCache(request), String.class);
    }

    public Mono<ServerResponse> updateCache(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(redisCacheService.updateCache(request), String.class);
    }

    public Mono<ServerResponse> deleteCache(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(redisCacheService.deleteCache(request), String.class);
    }

    public Mono<ServerResponse> getAllCache(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(redisCacheService.getAllCache(request), RedisModel.class);
    }

    public Mono<ServerResponse> getAllKeys(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(redisCacheService.getAllKeys(request), RedisModel.class);
    }
    public Mono<ServerResponse> getAllByKeys(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(redisCacheService.getAllByKeys(request), RedisModel.class);
    }

    public Mono<ServerResponse> deleteAllCache(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(redisCacheService.deleteAllCache(request), RedisModel.class);
    }

    public Mono<ServerResponse> getSizeCache(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(redisCacheService.getSizeCache(request), RedisModel.class);
    }
}
