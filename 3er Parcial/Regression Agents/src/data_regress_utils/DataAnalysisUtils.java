package data_regress_utils;

public class DataAnalysisUtils {



    /**
     * Calculates the mean of an array of doubles.
     *
     * @param values The array of double values.
     * @return The mean of the given values.
     */
    public static double calculateMean(double[] values) {
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

}

