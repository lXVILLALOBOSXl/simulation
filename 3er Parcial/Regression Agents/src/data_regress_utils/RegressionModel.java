package data_regress_utils;

public abstract class RegressionModel {
    protected double[] beta;

    protected Double determinationCoefficient;


    public RegressionModel(double[] beta) {
        this.beta = beta;
    }




}
