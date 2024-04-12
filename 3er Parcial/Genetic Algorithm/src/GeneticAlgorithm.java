import utility.DataSet;
import utility.RandomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GeneticAlgorithm {
    private int populationSize;
    private double crossoverRate;
    private double mutationRate;
    private int elitismCount;
    private Population population;
    private DataSet dataSet;

    public GeneticAlgorithm(int populationSize, double crossoverRate, double mutationRate, int elitismCount, DataSet dataSet) {
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.elitismCount = elitismCount;
        this.dataSet = dataSet;
        this.population = new Population(populationSize);  // Assuming population is initialized here
    }

    public RegressionModel train() {
        // Implement the main steps of the genetic algorithm

        // Randomly generate the initial population of chromosomes (or candidate solutions)
        this.initializePopulation();
        // Define the fitness function for evaluating chromosomes (or candidate solutions)
        for (Chromosome chromosome: this.population.getChromosomes()){
            this.calculateFitness(chromosome);
        }
        //  Randomly Select Parental Chromosomes
        Chromosome optimalCandidate = null;
        do{
            optimalCandidate = this.selectAndReproduce();
        }while (optimalCandidate == null);

        return new RegressionModel(optimalCandidate.getGenes(), optimalCandidate.getFitness());
    }

    private void initializePopulation() {
        this.population.initialize();

        for (int i = 0; i < this.populationSize - 1; i++) {
            double[] genes = new double[]{
                    RandomGenerator.getRandomDouble(1,200),
                    RandomGenerator.getRandomDouble(0,50)
            };
            Chromosome chromosome = new Chromosome(genes);
            this.population.addChromosome(chromosome);
        }

    }

    private void evaluateFitness() {
    }

    private double calculateFitness(Chromosome chromosome) {
        // Implement fitness calculation using dataSet and chromosome's genes
        return chromosome.calculateFitness(this.dataSet);
    }

    private Chromosome selectAndReproduce() {
        // Implement selection and reproduction process
        Chromosome parent1 = null;
        for (Chromosome chromosome: this.population.getChromosomes()){
            double randomNumber = RandomGenerator.getRandomDouble(0,1);
            if (randomNumber <= this.crossoverRate){
                chromosome.selected = true;
                parent1 = chromosome;
                break;
            }
        }
        Chromosome parent2 = rouletteWheelSelection();
        while (parent2 == parent1){
            parent2 = rouletteWheelSelection();
        }
        this.crossOver(parent1,parent2);
        Chromosome offSpring1 = parent1;
        Chromosome offSpring2 = parent2;
        this.mutate(offSpring1, offSpring2);

        if(offSpring1.getFitness() > 0.95){
            return offSpring1;
        } else if (offSpring2.getFitness() > 0.95) {
            return offSpring2;
        }else {
            offSpring1.selected = false;
            offSpring2.selected = false;
            return null;
        }


    }

    private void mutate(Chromosome c1, Chromosome c2) {
        int len = c1.getSize() < c2.getSize() ? c1.getSize() : c2.getSize();
        int randomIndex = RandomGenerator.getRandomInt(0,len-1);
        c1.setGen(randomIndex, this.mutateGene( c1.getGen(randomIndex)));
        c2.setGen(randomIndex, this.mutateGene( c2.getGen(randomIndex)));
    }

    // Method to mutate a single gene
    private double mutateGene(double gene) {
        // Randomly decide to add or subtract the mutation
        Random rand = new Random();
        // Calculate the mutation amount
        double mutationAmount = gene * this.mutationRate;

        // Randomly decide whether to add or subtract the mutation amount
        if (rand.nextBoolean()) {
            gene += mutationAmount;
        } else {
            gene -= mutationAmount;
        }

        return gene;
    }

    private Chromosome rouletteWheelSelection() {
        List<Chromosome> chromosomes = this.population.getChromosomes();
        double totalFitness = chromosomes.stream()
                .mapToDouble(Chromosome::getFitness)
                .sum();

        // Compute the probabilities (slot sizes)
        List<Double> probabilities = chromosomes.stream()
                .map(ch -> ch.getFitness() / totalFitness)
                .collect(Collectors.toList());

        // Calculate cumulative probabilities
        List<Double> cumulativeProbabilities = new ArrayList<>();
        double cumulative = 0.0;
        for (Double probability : probabilities) {
            cumulative += probability;
            cumulativeProbabilities.add(cumulative);
        }

        // Generate a random number between 0 and 1
        double r = RandomGenerator.getRandomDouble(0, 1);

        // Select the chromosome where the random number falls within its cumulative probability
        for (int i = 0; i < cumulativeProbabilities.size(); i++) {
            if (r <= cumulativeProbabilities.get(i) &&  !chromosomes.get(i).selected) {
                chromosomes.get(i).selected = true;
                return chromosomes.get(i);
            }
        }

        // In the unlikely event that no chromosome is selected (due to rounding errors), return the last one
        return chromosomes.get(chromosomes.size() - 1);

    }

    private void crossOver(Chromosome c1, Chromosome c2){
        int len = c1.getSize() < c2.getSize() ? c1.getSize() : c2.getSize();
        int randomIndex = RandomGenerator.getRandomInt(0,len-1);
        double auxGen = c1.getGen(randomIndex);
        c1.setGen(randomIndex, c2.getGen(randomIndex));
        c2.setGen(randomIndex, auxGen);
    }
}

