package examples.behaviours.com.maths;

import examples.behaviours.com.data.DataSet;

public class DataAnalysisUtils {

    /**
     * Calculates the determination coefficient (R^2) for a multiple linear regression model.
     * @param dataSet The DataSet object containing x and y arrays.
     * @param betas The regression coefficients calculated from the regression model.
     * @return the determination coefficient.
     */
    public static double calculateDeterminationCoefficient(DataSet dataSet, double[] betas) {
        double[][] x = dataSet.getX();
        double[] y = dataSet.getY();
        double[] yPredicted = new double[y.length];
        double sst = 0, ssr = 0, meanY = 0;

        // Calculate mean of y
        for (double v : y) {
            meanY += v;
        }
        meanY /= y.length;

        // Calculate predicted values and SSR
        for (int i = 0; i < y.length; i++) {
            double predicted = 0;
            for (int j = 0; j < x[i].length; j++) {
                predicted += x[i][j] * betas[j];
            }
            yPredicted[i] = predicted;
            ssr += (y[i] - predicted) * (y[i] - predicted);
            sst += (y[i] - meanY) * (y[i] - meanY);
        }

        return 1 - (ssr / sst);
    }

}

