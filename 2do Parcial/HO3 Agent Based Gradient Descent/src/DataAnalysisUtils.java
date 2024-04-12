public class DataAnalysisUtils {

    /**
     * Calculates the determination coefficient (R^2) for a simple linear regression model.
     * @param dataSet The DataSet object containing x and y arrays.
     * @param beta0 The intercept of the regression line.
     * @param beta1 The slope of the regression line.
     * @return the determination coefficient.
     */
    public static double calculateDeterminationCoefficient(DataSet dataSet, double beta0, double beta1) {
        double[] x = dataSet.getX();
        double[] y = dataSet.getY();

        if (x.length == 0 || y.length == 0 || x.length != y.length) {
            throw new IllegalArgumentException("DataSet is invalid or does not have enough data points");
        }

        double sumSST = 0.0;
        double sumSSR = 0.0;
        double meanY = 0.0;

        for (double yi : y) {
            meanY += yi;
        }
        meanY /= y.length;

        for (int i = 0; i < x.length; i++) {
            double yi = y[i];
            double yHat = beta1 * x[i] + beta0; // Calculate predicted y
            sumSST += (yi - meanY) * (yi - meanY);
            sumSSR += (yi - yHat) * (yi - yHat);
        }

        return 1 - (sumSSR / sumSST);
    }

}

