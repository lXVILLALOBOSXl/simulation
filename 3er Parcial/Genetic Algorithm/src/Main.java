import utility.DataSet;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
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

}
