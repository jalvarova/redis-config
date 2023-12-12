package org.redis.manager.rediscache.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.redis.manager.rediscache.messaging.CachePublish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

import static org.redis.manager.rediscache.util.CacheUtil.generateRandomLetters;

@EnableScheduling
@Slf4j
@Component
public class ScheduleEventMessage {

    @Autowired
    private CachePublish publish;
    @Autowired
    private ObjectMapper convert;

    @Scheduled(cron = "${schedule.cron}")
    public void eventMessageCache() throws JsonProcessingException {

        String generateWord = generateRandomLetters();
        log.info("Palabras " + generateWord);
        String key = "walavo:" + UUID.randomUUID();
        publish.sendMessage(convert.writeValueAsString(Map.of("key", key, "value", generateWord)));
    }
}
