package data_regress_utils;

import java.io.Serializable;
import java.util.Arrays;

public class DataSet implements Serializable {
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
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int i = 0; i < y.length; i++) {
            sb.append("| ").append(y[i]).append(" | ");
            for (int j = 0; j < x[i].length; j++) {
                sb.append(x[i][j]).append(" | ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }


}
