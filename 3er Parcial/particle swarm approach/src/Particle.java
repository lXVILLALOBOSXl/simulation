import util.DataAnalysisUtils;
import util.DataSet;
import util.Vector;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.Random;

public class Particle {

    private Vector oldCoordinates;
    private Vector coordinates;
    private Vector personalBest;
    private double fitness;
    private Vector direction;
    private Vector oldVelocity;
    private Vector velocity;

    public Particle(double[] coordinates) {
        Vector vector = new Vector(coordinates);
        this.coordinates = vector;
        this.oldCoordinates = vector;
        this.oldVelocity = randomCoordinates();
        this.velocity = randomCoordinates();
        this.personalBest = vector;
        this.direction = randomCoordinates();
    }

    public Vector randomCoordinates() {
        double[] randomCoordinates = new double[coordinates.length];
        for (int i = 0; i < coordinates.length; i++) {
            randomCoordinates[i] = new Random().nextDouble();
        }
        return new Vector(randomCoordinates);
    }

    public void move(double inertiaWeight, double cognitiveWeight, double socialWeight, Particle globalBest) {
        this.velocity = Vector.sum(Vector.sum(Vector.multiply(inertiaWeight, this.velocity),
                Vector.multiply(cognitiveWeight, Vector.multiply(new Random().nextDouble(), Vector.sub(this.personalBest, this.coordinates)))),
                Vector.multiply(socialWeight, Vector.multiply(new Random().nextDouble(), Vector.sub(globalBest.getPersonalBest(), this.coordinates))));
        this.coordinates = Vector.sum(this.oldCoordinates, this.velocity);
    }

    public void setFitness(DataSet dataset){
        this.fitness = DataAnalysisUtils.calculateDeterminationCoefficient(dataset,coordinates.getCoordinates());
    }

    public Vector getPersonalBest() {
        return personalBest;
    }

    public double getFitness() {
        return fitness;
    }

    public double[] getCoordinates() {
        return coordinates.getCoordinates();
    }

    @Override
    public String toString() {
        return coordinates.toString();
    }
}
