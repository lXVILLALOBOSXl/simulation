import utility.DataSet;

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
        // ucofbModel.toString();

    }
}
