package util;

public class DataAnalysisUtils {

    /**
     * Calculates the coefficient of determination (R^2) for the given dataset and model coefficients.
     *
     * @param dataSet The dataset containing the actual values.
     * @param betas The coefficients of the regression model.
     * @return The determination coefficient (R^2).
     */
    public static double calculateDeterminationCoefficient(DataSet dataSet, double[] betas) {
        double[][] xValues = dataSet.getX();
        double[] yValues = dataSet.getY();
        double sumOfSquaredErrors = 0.0;
        double sumOfSquaredTotal = 0.0;
        double yMean = calculateMean(yValues);

        for (int i = 0; i < yValues.length; i++) {
            double predicted = betas[0];
            for (int j = 0; j < xValues[i].length; j++) {
                predicted += betas[j + 1] * xValues[i][j];
            }
            double error = yValues[i] - predicted;
            sumOfSquaredErrors += error * error;
            double totalError = yValues[i] - yMean;
            sumOfSquaredTotal += totalError * totalError;
        }

        return 1 - (sumOfSquaredErrors / sumOfSquaredTotal);
    }

    /**
     * Calculates the mean of an array of doubles.
     *
     * @param values The array of double values.
     * @return The mean of the given values.
     */
    private static double calculateMean(double[] values) {
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }
}
