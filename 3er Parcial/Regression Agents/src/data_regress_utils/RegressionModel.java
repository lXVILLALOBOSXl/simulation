package data_regress_utils;

public class RegressionModel {
    protected double[] beta;

    private Double determinationCoefficient;

    private Double correlationCoefficient;

    private Double fitness;

    public RegressionModel(double[] beta, Double determinationCoefficient) {
        this.beta = beta;
        this.determinationCoefficient = determinationCoefficient;
        this.correlationCoefficient = Math.sqrt(determinationCoefficient);
    }


    public Double getFitness(DataSet dataSet){
        if (this.fitness == null) {
            this.fitness = DataAnalysisUtils.calculateDeterminationCoefficient(dataSet, this.beta);
        }
        return this.fitness;
    }

    @Override
    public String toString() {
        StringBuilder equation = new StringBuilder("Y = ");
        for (int i = 0; i < beta.length; i++) {
            if (i == 0) {
                equation.append(beta[i]);
            } else {
                equation.append(" + ").append(beta[i]).append("X").append(i);
            }
        }
        equation.append(" + e");
        return equation.toString();
    }

    public Double getDeterminationCoefficient() {
        return determinationCoefficient;
    }

    public Double getCorrelationCoefficient() {
        return correlationCoefficient;
    }
}
