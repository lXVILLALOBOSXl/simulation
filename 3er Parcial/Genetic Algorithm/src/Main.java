import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import utility.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main extends Agent {
    protected void setup() {
        System.out.println("Agent "+ getLocalName() + " started.");
        addBehaviour(new GeneticModel());
    }

    private class GeneticModel extends OneShotBehaviour {

        public void action() {
            DataSet unitedColorsOfBenettonDS = new DataSet(
                    new double[]{23, 26, 30, 34, 43, 48, 52, 57, 58},
                    new double[]{651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518}
            );

            GeneticAlgorithm ga = new GeneticAlgorithm(
                    100, 0.95, 0.01, 0, unitedColorsOfBenettonDS
            );
            RegressionModel ucofbModel = ga.train();
            System.out.println("Equation: " + ucofbModel.toString());
            System.out.println("Determination Coefficient = " + ucofbModel.getDeterminationCoefficient());
            System.out.println("Correlation Coefficient = " + ucofbModel.getCorrelationCoefficient());

            double n = 65;
            System.out.println("Predict for " + n + ": " + ucofbModel.predict(n));
            n = 78;
            System.out.println("Predict for " + n + ": " + ucofbModel.predict(n));
            n = 82;
            System.out.println("Predict for " + n + ": " + ucofbModel.predict(n));
        }

        public int onEnd() {
            myAgent.doDelete();
            System.out.println("Agent "+ myAgent.getLocalName() + " ended.");
            return super.onEnd();
        }
    }    // END of inner class ...Behaviour
}