package com.maths;

import com.data.DataSet;

import java.util.ArrayList;

public abstract class RegressionModel {
    protected double[] betas;
    protected DataSet dataSet;

    public RegressionModel(DataSet data) {
        dataSet = data;
    }

    public abstract String equation();

    public abstract double predict(double x);

}
