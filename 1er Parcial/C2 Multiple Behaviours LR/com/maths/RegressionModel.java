package examples.behaviours.com.maths;

import examples.behaviours.com.data.DataSet;

public abstract class RegressionModel {
    protected double[] betas;
    protected DataSet dataSet;

    public RegressionModel(DataSet data) {
        dataSet = data;
    }

    public abstract String equation();

    public abstract double predict(double[] x);

}
