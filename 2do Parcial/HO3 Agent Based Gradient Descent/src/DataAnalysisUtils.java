public class DataAnalysisUtils {

    /**
     * Calculates the Pearson correlation coefficient between the first variable in x and y of a DataSet object.
     * @param dataSet The DataSet object containing x and y arrays.
     * @return the correlation coefficient.
     */
    public static double calculateCorrelationCoefficient(DataSet dataSet) {
        double[] x = dataSet.getX(); // Changed to a single-dimensional array
        double[] y = dataSet.getY();

        if (x.length == 0 || y.length == 0) { // Adjusted the check since x is now one-dimensional
            throw new IllegalArgumentException("DataSet is invalid or does not have enough variables");
        }

        double sumX = 0.0, sumY = 0.0, sumXY = 0.0;
        double sumXX = 0.0, sumYY = 0.0;
        int n = y.length;

        for (int i = 0; i < n; i++) {
            double xi = x[i]; // Directly use x[i] since it's now one-dimensional
            double yi = y[i];

            sumX += xi;
            sumY += yi;
            sumXY += xi * yi;
            sumXX += xi * xi;
            sumYY += yi * yi;
        }

        double numerator = n * sumXY - sumX * sumY;
        double denominator = Math.sqrt((n * sumXX - sumX * sumX) * (n * sumYY - sumY * sumY));

        if (denominator == 0) {
            throw new ArithmeticException("Division by zero in correlation coefficient calculation");
        }

        return numerator / denominator;
    }


    /**
     * Calculates the determination coefficient (R^2) between the second variable in x and y of a DataSet object.
     * @param dataSet The DataSet object containing x and y arrays.
     * @return the determination coefficient.
     */
    public static double calculateDeterminationCoefficient(DataSet dataSet) {
        // First, calculate the Pearson correlation coefficient
        double r = calculateCorrelationCoefficient(dataSet);
        // Then, square it to get the determination coefficient R^2
        return r * r;
    }

}

