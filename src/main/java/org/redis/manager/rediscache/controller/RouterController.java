package org.redis.manager.rediscache.controller;

import org.redis.manager.rediscache.controller.routing.RedisCacheHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterController {

    @Autowired
    private RedisCacheHandler redisCacheHandler;

    @Bean
    RouterFunction<ServerResponse> routes() {
        return route(GET("/cache").and(accept(MediaType.APPLICATION_JSON)), redisCacheHandler::getCache)
                .andRoute(POST("/cache").and(accept(MediaType.APPLICATION_JSON)), redisCacheHandler::createCache)
                .andRoute(PUT("/cache").and(accept(MediaType.APPLICATION_JSON)), redisCacheHandler::updateCache)
                .andRoute(DELETE("/cache/{keyPattern}/key/{key}").and(accept(MediaType.APPLICATION_JSON)), redisCacheHandler::deleteCache)
                .andRoute(GET("/caches/keys/{key}").and(accept(MediaType.APPLICATION_JSON)), redisCacheHandler::getAllKeys)
                .andRoute(POST("/caches/keys").and(accept(MediaType.APPLICATION_JSON)), redisCacheHandler::getAllByKeys)
                .andRoute(GET("/caches/{key}").and(accept(MediaType.APPLICATION_JSON)), redisCacheHandler::getAllCache)
                .andRoute(DELETE("/caches").and(accept(MediaType.APPLICATION_JSON)), redisCacheHandler::deleteAllCache)
                .andRoute(GET("/db").and(accept(MediaType.APPLICATION_JSON)), redisCacheHandler::getSizeCache)
                .andRoute(GET("/info").and(accept(MediaType.APPLICATION_JSON)), redisCacheHandler::getPropertiesCache)
                .andRoute(GET("/time").and(accept(MediaType.APPLICATION_JSON)), redisCacheHandler::getTimeCache)
                .andRoute(GET("/clients").and(accept(MediaType.APPLICATION_JSON)), redisCacheHandler::getClientCache);

    }
}
