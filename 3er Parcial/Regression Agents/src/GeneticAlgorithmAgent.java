import data_regress_utils.DataSet;
import data_regress_utils.PolynomialRegressionModel;
import data_regress_utils.RegressionModel;
import genetic_algorithm.Chromosome;
import genetic_algorithm.Population;
import genetic_algorithm.RandomGenerator;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GeneticAlgorithmAgent extends Agent {

    private int populationSize = 100;
    private double crossoverRate = 0.95;
    private double mutationRate = 0.01;
    private int elitismCount = 0;
    private Population population = new Population(populationSize);;
    private Population recombinedPopulation = new Population(populationSize);
    private Double[][] ranges = new Double[][]{{1.0, 200.0}, {0.0, 50.0}, {0.0, 50.0}, {0.0, 50.0}};
    private DataSet dataSet;

    protected void setup() {

        System.out.println("Agent "+ getLocalName() + " started.");

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("regression");
        sd.setName("genetic-algorithm");
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new offerRequestsServer());
        addBehaviour(new modelOrdersServer());
    }


    private class offerRequestsServer extends CyclicBehaviour{

        @Override
        public void action() {


            recombinedPopulation.initialize();

            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);

            if (msg != null) {
                ACLMessage reply = msg.createReply();
                try {
                    if (msg.getContentObject() instanceof DataSet) {
                        dataSet = (DataSet) msg.getContentObject();
                        double[][] xValues = dataSet.getX();
                        boolean hasMoreThanOneElement = false;
                        for (double[] x : xValues) {
                            if (x.length > 1) {
                                hasMoreThanOneElement = true;
                                break;
                            }
                        }
                        if (hasMoreThanOneElement) {
                            reply.setPerformative(ACLMessage.REFUSE);
                            reply.setContent("unknown-action");
                        } else {
                            PolynomialRegressionModel regressionModel = train();
                            reply.setPerformative(ACLMessage.PROPOSE);
                            reply.setContent(regressionModel.getFitness(dataSet).toString());
                        }
                    } else {
                        reply.setPerformative(ACLMessage.REFUSE);
                        reply.setContent("unknown-action");
                    }
                    myAgent.send(reply);
                }catch (UnreadableException e){
                    e.printStackTrace();
                }

            } else {
                block();
            }
        }
    }

    private class modelOrdersServer extends CyclicBehaviour {
        @Override
        public void action() {


            recombinedPopulation.initialize();

            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                ACLMessage reply = msg.createReply();
                try {
                    if (msg.getContentObject() instanceof DataSet) {
                        try {
                            dataSet = (DataSet) msg.getContentObject();
                        } catch (UnreadableException e) {
                            e.printStackTrace();
                        }
                        RegressionModel regressionModel = train();
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent(regressionModel.toString());
                    } else {
                        reply.setPerformative(ACLMessage.FAILURE);
                        reply.setContent("unknown-action");
                    }
                }catch (UnreadableException e){
                    e.printStackTrace();
                }
                myAgent.send(reply);
                reply.setPerformative(ACLMessage.INFORM);
            } else {
                block();
            }
        }
    }

    // Put agent clean-up operations here
    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
        // Printout a dismissal message
        System.out.println("Seller-agent "+getAID().getName()+" terminating.");
    }


    public PolynomialRegressionModel train() {
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



        return new PolynomialRegressionModel(selected.getGenes());
    }

    private void initializePopulation() {
        this.population.initialize();
        for (int i = 0; i < this.populationSize - 1; i++) {
            double[] genes = new double[this.ranges.length];
            for (int j = 0; j < this.ranges.length; j++) {
                genes[j] = RandomGenerator.getRandomDouble(this.ranges[j][0], this.ranges[j][1]);
            }
            Chromosome chromosome = new Chromosome(genes);
            this.population.addChromosome(chromosome);
        }
    }

    private double calculateFitness(Chromosome chromosome) {
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
