package utility;

import java.util.Random;

public class RandomGenerator {
    /**
     * Generates a random double between the specified min and max values.
     *
     * @param min the minimum value (inclusive)
     * @param max the maximum value (exclusive)
     * @return a random double between min and max
     */
    public static double getRandomDouble(double min, double max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }
}
