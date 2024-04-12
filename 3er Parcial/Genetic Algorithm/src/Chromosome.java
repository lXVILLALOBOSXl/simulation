import utility.DataSet;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Chromosome {
    private double[] genes;
    private double fitness;

    public Chromosome(double[] genes) {
        this.genes = genes;
    }

    public double calculateFitness(DataSet dataSet) {
        double[] x = dataSet.getX();
        double[] y = dataSet.getY();

        if (x.length == 0 || y.length == 0 || x.length != y.length) {
            throw new IllegalArgumentException("DataSet is invalid or does not have enough data points");
        }

        double sumSST = 0.0;
        double sumSSR = 0.0;
        double meanY = 0.0;

        for (double yi : y) {
            meanY += yi;
        }
        meanY /= y.length;

        for (int i = 0; i < x.length; i++) {
            double yi = y[i];
            double yHat = genes[1] * x[i] + genes[0]; // Calculate predicted y
            sumSST += (yi - meanY) * (yi - meanY);
            sumSSR += (yi - yHat) * (yi - yHat);
        }

        this.fitness = 1 - (sumSSR / sumSST);
        return this.fitness;
    }

    public void mutate() {}

    public Chromosome clone() { return null; }

    @Override
    public String toString() {
        return Arrays.stream(genes)
                .mapToObj(Double::toString)
                .collect(Collectors.joining(",", "[", "]"));
    }
}
