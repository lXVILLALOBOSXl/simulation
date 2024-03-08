public abstract class RegressionModel {
    protected double[] beta;
    protected DataSet dataSet;

    public RegressionModel(DataSet data) {
        dataSet = data;
    }

    public abstract String equation();

    public abstract double predict(double x);

}
