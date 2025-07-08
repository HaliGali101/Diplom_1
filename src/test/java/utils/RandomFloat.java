package utils;

import java.util.Random;

public class RandomFloat {

    private static final Random random = new Random();

    public static float generatePositivePrice() {
        return 1.0f + random.nextFloat() * 9.0f; // [1, 10)
    }

    public static float generateNegativePrice() {
        return -1.0f * (1.0f + random.nextFloat() * 9.0f); // [-10, -1)
    }
}
