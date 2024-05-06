package data_regress_utils;

import static data_regress_utils.DataAnalysisUtils.calculateMean;

public class PolynomialRegressionModel extends RegressionModel {

    public PolynomialRegressionModel(double[] beta) {
        super(beta);
    }

    @Override
    public String toString() {
        StringBuilder equation = new StringBuilder("Y = ");
        for (int i = 0; i < beta.length; i++) {
            if (i == 0) {
                equation.append(beta[i]);
            } else {
                equation.append(" + ").append(beta[i]).append("X1^").append(i);
            }
        }
        equation.append(" + e");
        return equation.toString();
    }

    public Double getFitness(DataSet dataSet){
        if (this.determinationCoefficient == null) {
            this.determinationCoefficient = calculateDeterminationCoefficient(dataSet, this.beta);
        }
        return this.determinationCoefficient;
    }

    public static double calculateDeterminationCoefficient(DataSet dataSet, double[] betas) {
        double[][] xValues = dataSet.getX();
        double[] yValues = dataSet.getY();
        double sumOfSquaredErrors = 0.0;
        double sumOfSquaredTotal = 0.0;
        double yMean = calculateMean(yValues);

        for (int i = 0; i < yValues.length; i++) {
            double predicted = betas[0];
            for (int j = 1; j < betas.length; j++) {
                predicted += betas[j] * Math.pow(xValues[i][0], j);
            }
            double error = yValues[i] - predicted;
            sumOfSquaredErrors += error * error;
            double totalError = yValues[i] - yMean;
            sumOfSquaredTotal += totalError * totalError;
        }

        return 1 - (sumOfSquaredErrors / sumOfSquaredTotal);
    }
}