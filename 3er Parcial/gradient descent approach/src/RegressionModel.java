public abstract class RegressionModel {
    protected double[] beta;
    protected DataSet dataSet;

    private Double determinationCoefficient;

    private Double correlationCoefficient;

    public RegressionModel(DataSet data) {
        dataSet = data;
    }

    public abstract String equation();

    public abstract double predict(double x);

    public Double getDeterminationCoefficient() {
        this.determinationCoefficient = DataAnalysisUtils.calculateDeterminationCoefficient(this.dataSet,this.beta);
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
