package com.maths;

import com.data.DataSet;

public class MultipleLinearRegresion extends RegressionModel{

    public MultipleLinearRegresion(DataSet ds, int grade) {
        super(ds);
        this.dataSet = transformDataSet(ds, grade);
        calculateBetas();
    }

    private DataSet transformDataSet(DataSet ds, int grade) {
        double[][] x = ds.getX();
        double[][] transformedX = new double[x.length][];

        for (int i = 0; i < x.length; i++) {
            transformedX[i] = new double[grade + 1];
            transformedX[i][0] = 1; // Add 1 to every array in x
            for (int j = 1; j <= grade; j++) {
                transformedX[i][j] = Math.pow(x[i][0], j); // Add the pow functions until n specified
            }
        }

        return new DataSet(transformedX, ds.getY());
    }


    @Override
    public String equation() {
        String equation = "Y = " + betas[0] + " + ";

        for (int i = 1; i < betas.length; i++){
            equation += (betas[i] + "*x^" + (i) + " + ");
        }
        equation += "e";
        return equation;
    }

    @Override
    public double predict(double x) {
        double prediction = betas[0];
        for (int i = 1; i < betas.length; i++){
            prediction += (betas[i] * (Math.pow(x,i)));
        }
        return prediction;
    }

    private void calculateBetas() {
        betas = LinearAlgebra.matrixVectorMultiplication(LinearAlgebra.inverseMatrix(LinearAlgebra.matrixMultiplication((LinearAlgebra.transposeMatrix(dataSet.getX())),dataSet.getX())),
                LinearAlgebra.matrixVectorMultiplication((LinearAlgebra.transposeMatrix(dataSet.getX())),dataSet.getY()));
    }
}
