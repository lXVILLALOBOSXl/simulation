import util.DataSet;

public class Boot {
    public static void main(String[] args) {
        DataSet dataSet = new DataSet(
                new double[][]{{23}, {26}, {30}, {34}, {43}, {48}, {52}, {57}, {58}},
                new double[]{651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518}
        );
        Double[][] ranges = new Double[][]{{1.0, 200.0}, {0.0, 50.0}};

        Swarm swarm = new Swarm(dataSet, ranges, 20, 0.9, 0.9, 0.9);
        RegressionModel regressionModel = swarm.train();
        System.out.println("Equation: " + regressionModel.toString());

    }
}
