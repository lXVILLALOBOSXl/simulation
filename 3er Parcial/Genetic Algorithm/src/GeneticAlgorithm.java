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
    private Population recombinedPopulation;
    private DataSet dataSet;

    public GeneticAlgorithm(int populationSize, double crossoverRate, double mutationRate, int elitismCount, DataSet dataSet) {
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.elitismCount = elitismCount;
        this.dataSet = dataSet;
        this.population = new Population(populationSize);  // Assuming population is initialized here
        this.recombinedPopulation = new Population(populationSize);
        this.recombinedPopulation.initialize();
    }

    public RegressionModel train() {
        // Implement the main steps of the genetic algorithm

        // Randomly generate the initial population of chromosomes (or candidate solutions)
        this.initializePopulation();
        // Define the fitness function for evaluating chromosomes (or candidate solutions)
        for (Chromosome chromosome: this.population.getChromosomes()){
            this.calculateFitness(chromosome);
        }
        Chromosome selected = null;
        do {
            //  Randomly Select Parental Chromosomes
            Chromosome[] parents = this.select();
            // Crossover the selected chromosomes to produce offspring
            Chromosome[] offSpring = this.crossOver(parents[0], parents[1]);
            // add the offspring to the recombined population
            this.recombinedPopulation.addChromosome(new Chromosome(offSpring[0].getGenes()));
            this.recombinedPopulation.addChromosome(new Chromosome(offSpring[1].getGenes()));
            // Select into the recombined population to mutate
            selected = this.selectToMutate(this.recombinedPopulation);
            // Mutate the selected chromosome
            if (selected != null) {
                this.mutate(selected);
            }
        } while (selected == null || this.calculateFitness(selected) < 0.95 ); // Evaluate the fitness of the mutated chromosome



        return new RegressionModel(selected.getGenes(), selected.getFitness());
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

    private Chromosome[] select() {
        // Implement selection and reproduction process
        Chromosome parent1 = null;
        for (Chromosome chromosome: this.population.getChromosomes()){
            double randomNumber = RandomGenerator.getRandomDouble(0,1);
            if (randomNumber <= this.crossoverRate && !chromosome.selected){
                chromosome.selected = true;
                parent1 = chromosome;
                break;
            }
        }
        Chromosome parent2 = rouletteWheelSelection();
        while (parent2 == parent1 || parent2.selected){
            parent2 = rouletteWheelSelection();
        }
        parent2.selected = true;
        return new Chromosome[]{parent1, parent2};

    }



    private Chromosome selectToMutate(Population populationA) {
        // Implement mutation process
        // Get the first chromosome in the population and check if it wasn´t selected
        for (Chromosome chromosome: populationA.getChromosomes()){
            //Calculate a random number between 0 and 0.1
            double randomNumber = RandomGenerator.getRandomDouble(0,0.1);
            // If the random number is less than the mutation rate and the current chromosome wasn't selected, mutate the chromosome
            if (randomNumber <= this.mutationRate && !chromosome.selected){
                return chromosome;
            }
        }
        return null;
    }

    // Method to mutate a single gene
    private void mutate(Chromosome chromosome){
        //Calulate a random index to mutate
        int randomIndex = RandomGenerator.getRandomInt(0,chromosome.getSize()-1);
        //Calculate a random number to replace the gene
        double randomNumber = 0;
        if (randomIndex == 0){
            randomNumber = RandomGenerator.getRandomDouble(1,200);
        }else if (randomIndex == 1){
            randomNumber = RandomGenerator.getRandomDouble(0,50);
        }
        //Replace the gene
        chromosome.setGen(randomIndex, randomNumber);
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
                return chromosomes.get(i);
            }
        }

        // In the unlikely event that no chromosome is selected (due to rounding errors), return the last one
        return chromosomes.get(chromosomes.size() - 1);

    }

    private Chromosome[] crossOver(Chromosome c1, Chromosome c2){
        int len = c1.getSize() < c2.getSize() ? c1.getSize() : c2.getSize();
        int randomIndex = RandomGenerator.getRandomInt(0,len-1);
        double auxGen = c1.getGen(randomIndex);
        c1.setGen(randomIndex, c2.getGen(randomIndex));
        c2.setGen(randomIndex, auxGen);
        c1.selected = false;
        c2.selected = false;
        return new Chromosome[]{c1, c2};
    }
}

