package genetic_algorithm;

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

    public static int getRandomInt(int x, int y){
        if (x > y) {  // Ensure x is always less than or equal to y
            int temp = x;
            x = y;
            y = temp;
        }

        Random random = new Random();
        int randomNumber = random.nextInt(y - x + 1) + x;
        return randomNumber;
    }
}
