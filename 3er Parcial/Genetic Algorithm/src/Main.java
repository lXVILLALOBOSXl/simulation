import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import utility.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends Agent {
    protected void setup() {
        System.out.println("Agent "+ getLocalName() + " started.");

        DataSet unitedColorsOfBenettonDS = new DataSet(
                new double[]{23, 26, 30, 34, 43, 48, 52, 57, 58},
                new double[]{651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518}
        );
        double[] toPredict = new double[]{65,78,62};
        addBehaviour(new GeneticModel(100, 0.95, 0.01, 0, unitedColorsOfBenettonDS,toPredict));
    }

    private class GeneticModel extends OneShotBehaviour {

        private int populationSize;
        private double crossoverRate;
        private double mutationRate;
        private int elitismCount;
        private DataSet dataSet;

        double[] toPredict;

        public GeneticModel(int populationSize, double crossoverRate, double mutationRate, int elitismCount, DataSet dataSet,double[] toPredict) {
            this.populationSize = populationSize;
            this.crossoverRate = crossoverRate;
            this.mutationRate = mutationRate;
            this.elitismCount = elitismCount;
            this.dataSet = dataSet;
            this.toPredict = toPredict;
        }

        public void action() {


            GeneticAlgorithm ga = new GeneticAlgorithm(
                    populationSize, crossoverRate, mutationRate, elitismCount, dataSet
            );
            RegressionModel ucofbModel = ga.train();
            System.out.println("Equation: " + ucofbModel.toString());
            System.out.println("Determination Coefficient = " + ucofbModel.getDeterminationCoefficient());
            System.out.println("Correlation Coefficient = " + ucofbModel.getCorrelationCoefficient());

            for (double n :
                    toPredict) {
                System.out.println("Predict for " + n + ": " + ucofbModel.predict(n));
            }

        }

        public int onEnd() {
            myAgent.doDelete();
            System.out.println("Agent "+ myAgent.getLocalName() + " ended.");
            return super.onEnd();
        }
    }    // END of inner class ...Behaviour
}