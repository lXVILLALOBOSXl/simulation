import java.util.Arrays;

public class RegressionModel {
    protected double[] beta;

    private Double determinationCoefficient;

    private Double correlationCoefficient;

    public RegressionModel(double[] beta, Double determinationCoefficient) {
        this.beta = beta;
        this.determinationCoefficient = determinationCoefficient;
        this.correlationCoefficient = Math.sqrt(determinationCoefficient);
    }

    public double predict(double x) {
        return this.beta[0] + (this.beta[1] * x);
    }

    @Override
    public String toString() {
        String equation = "Y = " + this.beta[0] + " + " +  this.beta[1] + "X + e";
        return equation;
    }

    public Double getDeterminationCoefficient() {
        return determinationCoefficient;
    }

    public Double getCorrelationCoefficient() {
        return correlationCoefficient;
    }
}
