package org.redis.manager.rediscache.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.redis.manager.rediscache.messaging.CachePublish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@EnableScheduling
@Slf4j
@Component
public class ScheduleEventMessage {

    @Autowired
    private CachePublish publish;
    @Autowired
    private ObjectMapper convert;

    @Scheduled(cron = "*/2 * * * * ?")
    public void eventMessageCache() throws JsonProcessingException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        String strDate = sdf.format(now);
        log.info("Java cron job expression:: " + strDate);
        String generateWord = generateRandomLetters();
        log.info("Palabras " + generateWord);
        String key = UUID.randomUUID().toString();

        publish.sendMessage(convert.writeValueAsString(Map.of(key, generateWord)));
    }

    private String generateRandomLetters() {
        return IntStream.range(0, 10)
                .mapToObj(i -> generateWord())
                .collect(Collectors.joining());
    }

    private String generateWord() {
        int rand = new Random().nextInt(52);
        char start = (rand < 26) ? 'A' : 'a';
        char charWord = (char) (start + rand % 26);
        return String.valueOf(charWord);
    }
}
