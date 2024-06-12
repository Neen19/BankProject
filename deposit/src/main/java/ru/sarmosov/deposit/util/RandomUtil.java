package ru.sarmosov.deposit.util;

import org.springframework.stereotype.Component;

import java.util.Random;


public class RandomUtil {

    private static final Random RANDOM = new Random();

    public static int getRandomCode() {
        return 1000 + RANDOM.nextInt(9000);
    }

}
