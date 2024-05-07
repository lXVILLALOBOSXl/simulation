public class DataAnalysisUtils {

    /**
     * Calculates the determination coefficient (R^2) for a simple linear regression model.
     * @param dataSet The DataSet object containing x and y arrays.
     * @param beta0 The intercept of the regression line.
     * @param beta1 The slope of the regression line.
     * @return the determination coefficient.
     */
    static double calculateDeterminationCoefficient(DataSet dataSet, double[] betas) {
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

    public static double calculateMean(double[] values) {
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

}

