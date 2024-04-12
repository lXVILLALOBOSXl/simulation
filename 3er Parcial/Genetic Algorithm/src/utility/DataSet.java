package utility;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataSet {
    private double x[];
    private double y[];

    private int n;

    public DataSet(double[] x, double[] y) {
        this.x = x;
        this.y = y;
        this.n = (x.length < y.length) ? x.length:y.length;
    }

    // Calculate the range of dataset
    public double[] range() {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for (double value : this.x) {
            if (value < min) min = value;
            if (value > max) max = value;
        }
        for (double value : this.y) {
            if (value < min) min = value;
            if (value > max) max = value;
        }

        return new double[]{min, max};
    }

    public double[]  getX() {
        return this.x;
    }

    public double[] getY() {
        return this.y;
    }

    public int getN() {
        return n;
    }

    @Override
    public String toString() {
        return IntStream.range(0, n)
                .mapToObj(i -> String.format("(%s,%s)", x[i], y[i]))
                .collect(Collectors.joining(", ", "[", "]"));
    }
}
