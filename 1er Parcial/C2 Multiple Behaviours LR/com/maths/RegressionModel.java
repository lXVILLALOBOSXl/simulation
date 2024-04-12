package examples.behaviours.com.maths;

import examples.behaviours.com.data.DataSet;

public abstract class RegressionModel {
    protected double[] betas;

    private Double determinationCoefficient;

    private Double correlationCoefficient;
    protected DataSet dataSet;

    public RegressionModel(DataSet data) {
        dataSet = data;
    }

    public abstract String equation();

    public abstract double predict(double[] x);

    public Double getDeterminationCoefficient() {
        this.determinationCoefficient = DataAnalysisUtils.calculateDeterminationCoefficient(this.dataSet,this.betas);
        return determinationCoefficient;
    }

    public Double getCorrelationCoefficient() {
        if (this.determinationCoefficient != null){
            this.getDeterminationCoefficient();
        }
        this.correlationCoefficient = Math.sqrt(this.determinationCoefficient);
        return this.correlationCoefficient;
    }

}
