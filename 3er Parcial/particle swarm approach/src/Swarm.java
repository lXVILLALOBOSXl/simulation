import util.DataSet;
import util.RandomGenerator;

public class Swarm {
   private Particle[] swarm;
   private Particle teamBest;
   private DataSet dataSet;
   private int numberOfParticles;
   private double cognitiveWeight;
   private double socialWeight;
   private double inertiaWeight;
   private Double[][] ranges;

    public Swarm(DataSet dataSet, Double[][] ranges, int numberOfParticles, double cognitiveWeight, double socialWeight, double inertiaWeight) {
        this.dataSet = dataSet;
        this.numberOfParticles = numberOfParticles;
        this.swarm = new Particle[numberOfParticles];
        this.cognitiveWeight = cognitiveWeight;
        this.socialWeight = socialWeight;
        this.inertiaWeight = inertiaWeight;
        this.ranges = ranges;
    }

    public RegressionModel train() {
        initializeSwarm();
        teamBest = getTeamBest();
        int i = 0;
        while (teamBest.getFitness() < 0.95){
            for (Particle particle : swarm) {
                particle.move(inertiaWeight, cognitiveWeight, socialWeight, teamBest);
                particle.setFitness(dataSet);
            }
            teamBest = getTeamBest();
            i++;
            System.out.println("Iteration: " + i + " Fitness: " + teamBest.getFitness());
        }
        return new RegressionModel(teamBest.getCoordinates(), teamBest.getFitness());

    }

    private Particle getTeamBest() {
        Particle teamBest = swarm[0];
        for (Particle particle : swarm) {
            if (particle.getFitness() > teamBest.getFitness()) {
                teamBest = particle;
            }
        }
        return teamBest;
    }

    private void initializeSwarm() {
        for (int i = 0; i < swarm.length; i++) {
            double[] coordinates = new double[dataSet.getNumberOfFeatures()+1];
            for (int j = 0; j < dataSet.getNumberOfFeatures()+1; j++) {
               coordinates[j] = RandomGenerator.getRandomDouble(ranges[j][0], ranges[j][1]);
            }
            Particle p = new Particle(coordinates);
            p.setFitness(dataSet);
            swarm[i] = p;
        }
    }
}
