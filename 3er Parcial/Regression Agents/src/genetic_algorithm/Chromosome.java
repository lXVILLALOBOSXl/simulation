package genetic_algorithm;

import data_regress_utils.DataAnalysisUtils;
import data_regress_utils.DataSet;
import data_regress_utils.PolynomialRegressionModel;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Chromosome {
    public boolean selected;
    private double[] genes;
    private Double fitness;

    private int size;

    public Chromosome(double[] genes) {
        this.genes = genes;
        this.selected=false;
        this.size = genes.length;
    }

    public Double calculateFitness(DataSet dataSet) {
        double[] betas = this.genes;
        this.fitness = PolynomialRegressionModel.calculateDeterminationCoefficient(dataSet, betas);
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
