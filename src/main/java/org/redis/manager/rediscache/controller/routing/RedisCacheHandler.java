package org.redis.manager.rediscache.controller.routing;

import lombok.RequiredArgsConstructor;
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
                .body(redisCacheService.getCache(), String.class);
    }

    public Mono<ServerResponse> createCache(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(redisCacheService.createCache(), String.class);
    }

    public Mono<ServerResponse> updateCache(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(redisCacheService.updateCache(), String.class);
    }

    public Mono<ServerResponse> deleteCache(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(redisCacheService.deleteCache(), String.class);
    }

    public Mono<ServerResponse> getAllCache(ServerRequest request) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(redisCacheService.getAllCache(), String.class);
    }
}
