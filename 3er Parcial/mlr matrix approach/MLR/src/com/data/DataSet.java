package com.data;

import java.util.Arrays;

public class DataSet {
    private double x[][];
    private double y[];

    public DataSet(double[][] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    public double[][]  getX() {
        return this.x;
    }

    public double[] getY() {
        return this.y;
    }


    @Override
    public String toString() {
        return "com.data.DataSet{" +
                "x=" + Arrays.toString(x) +
                ", y=" + Arrays.toString(y) +
                '}';
    }
}
