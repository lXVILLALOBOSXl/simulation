package examples.behaviours.com.maths;


import examples.behaviours.com.data.DataSet;

public class MultipleLinearRegresion extends RegressionModel{

    public MultipleLinearRegresion(DataSet data) {
        super(data);
        calculateBetas();
    }

    @Override
    public String equation() {
        String equation = "Y = " + betas[0] + " + ";

        for (int i = 1; i < betas.length; i++){
            equation += (betas[i] + "x" + (i) + " + ");
        }
        equation += "e";
        return equation;
    }

    @Override
    public double predict(double[] x) {
        double prediction = betas[0];
        for (int i = 1; i < betas.length; i++){
            prediction += (betas[i] * x[i-1]);
        }
        return prediction;
    }

    private void calculateBetas() {
        betas = LinearAlgebra.matrixVectorMultiplication(LinearAlgebra.inverseMatrix(LinearAlgebra.matrixMultiplication((LinearAlgebra.transposeMatrix(dataSet.getX())),dataSet.getX())),
                LinearAlgebra.matrixVectorMultiplication((LinearAlgebra.transposeMatrix(dataSet.getX())),dataSet.getY()));
    }
}
