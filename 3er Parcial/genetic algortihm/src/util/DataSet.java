package util;

public class DataSet {
    private double[][] x;  // Independent variables can be multidimensional
    private double[] y;    // Dependent variable remains one-dimensional

    public DataSet(double[][] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    public double[][] getX() {
        return x;
    }

    public double[] getY() {
        return y;
    }
}
