public class RegressionModel {
    private double[] dependentVariable;
    private double[][] independentVariables;

    public RegressionModel(double[] dependentVariable, double[][] independentVariables) {
        this.dependentVariable = dependentVariable;
        this.independentVariables = independentVariables;
    }

    public double predict(double[] inputs) { return 0.0; }

    public double calculateRSquared() { return 0.0; }
}
