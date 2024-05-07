public class Gradient extends RegressionModel{
    private double learningRate;

    // private double meanSquared;

    public Gradient(DataSet data) {
        super(data);

        this.beta = new double[2];

        this.beta[0] = 0;
        this.beta[1] = 0;
        this.learningRate = 0.0005;
    }

/*    private double meanSquared(){
        double sum = 0;
        double n = this.dataSet.getN();

        for (int i = 0; i < n; i++){
            sum += Math.pow((this.dataSet.getY()[i] - (this.beta[0] + (this.beta[1]*this.dataSet.getX()[i]))),2);
        }
        return (1/n) * (sum);
    }*/
private double calculateIntersect(){
    double sum = 0;
    double n = this.dataSet.getN();

    for (int i = 0; i < n; i++){
        sum += (this.dataSet.getY()[i] - (this.beta[0] + (this.beta[1]*this.dataSet.getX()[i][0])));
    }
    return (-2/n) * sum;
}

    private double calculateSlope(){
        double sum = 0;
        double n = this.dataSet.getN();

        for (int i = 0; i < n; i++){
            sum += (this.dataSet.getX()[i][0] * (this.dataSet.getY()[i] - (this.beta[0] + (this.beta[1]*this.dataSet.getX()[i][0]))));
        }
        return (-2/n) * sum;
    }

    public void train(int epochs){
        int epoch = 0;
        while (epoch < epochs){
            double newB0 = this.beta[0] - (this.learningRate * this.calculateIntersect());
            double newB1 = this.beta[1] - (this.learningRate * this.calculateSlope());
            this.beta[0] = newB0;
            this.beta[1] = newB1;
            epoch+=1;
        }
        System.out.println("Model trained");
    }


    @Override
    public String equation() {
        String equation = "Y = " + this.beta[0] + " + " +  this.beta[1] + "X + e";
        return equation;
    }

    @Override
    public double predict(double x) {
        return this.beta[0] + (this.beta[1] * x);
    }
}
