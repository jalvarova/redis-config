package org.redis.manager.rediscache.util;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class CacheUtil {

    public static String generateRandomLetters() {
        return IntStream.range(0, 10)
                .mapToObj(i -> generateWord())
                .collect(Collectors.joining());
    }

    private static String generateWord() {
        int rand = new Random().nextInt(52);
        char start = (rand < 26) ? 'A' : 'a';
        char charWord = (char) (start + rand % 26);
        return String.valueOf(charWord);
    }
}
