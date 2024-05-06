import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        DataSet dataSet = new DataSet(
                new double[][]{{23}, {26}, {30}, {34}, {43}, {48}, {52}, {57}, {58}},
                new double[]{651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518}
        );



        SLR slr = new SLR(dataSet);

        System.out.println(dataSet);
        System.out.println("b0: " + slr.getB0());
        System.out.println("b1: " + slr.getB1());
        System.out.println("Equation: " + slr.getEquation());
        System.out.print("Invest to predict: ");
        double n = s.nextDouble();
        System.out.println("Predict for " + n + ": " + slr.predict(n));
    }
}
