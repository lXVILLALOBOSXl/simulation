import utility.DataSet;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Chromosome {
    public boolean selected;
    private double[] genes;
    private double fitness;

    private int size;

    public Chromosome(double[] genes) {
        this.genes = genes;
        this.selected=false;
        this.size = genes.length;
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
        fitness = 1 - (sumSSR / sumSST);
        if (fitness < 0){
            fitness = 0;
        }
        this.fitness = fitness;
        return this.fitness;
    }

    public void mutate() {}

    public Chromosome clone() { return null; }

    public double getFitness() {
        return fitness;
    }

    public int getSize(){
        return size;
    }

    public double getGen(int index) { return this.genes[index]; }

    public double[] getGenes() {
        return genes;
    }

    public void setGen(int index, double newValue) {  this.genes[index] = newValue; }

    @Override
    public String toString() {
        return Arrays.stream(genes)
                .mapToObj(Double::toString)
                .collect(Collectors.joining(",", "[", "]"));
    }
}
