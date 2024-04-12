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
        // ucofbModel.toString();

    }

    /*public static void main(String[] args) {
        List<Double[]> dataPoints = new ArrayList<>();
        dataPoints.add(new Double[]{23.0, 651.0});
        dataPoints.add(new Double[]{26.0, 762.0});
        dataPoints.add(new Double[]{30.0, 856.0});
        dataPoints.add(new Double[]{34.0, 1063.0});
        dataPoints.add(new Double[]{43.0, 1190.0});
        dataPoints.add(new Double[]{48.0, 1298.0});
        dataPoints.add(new Double[]{52.0, 1421.0});
        dataPoints.add(new Double[]{57.0, 1440.0});
        dataPoints.add(new Double[]{58.0, 1518.0});

        double[] coefficients = calculateCoefficients(dataPoints);
        double b0 = coefficients[0];
        double b1 = coefficients[1];
        System.out.println(b0);
        System.out.println(b1);

        *//*double rangePercent = 0.30; // 30% range
        double b0Low = b0 - (b0 * rangePercent);
        double b0High = b0 + (b0 * rangePercent);
        double b1Low = b1 - (b1 * rangePercent);
        double b1High = b1 + (b1 * rangePercent);

        System.out.printf("Suggested range for b0: %.2f to %.2f%n", b0Low, b0High);
        System.out.printf("Suggested range for b1: %.2f to %.2f%n", b1Low, b1High);*//*
    }

    public static double[] calculateCoefficients(List<Double[]> dataPoints) {
        double sumX = 0, sumY = 0, sumXY = 0, sumXX = 0;
        int n = dataPoints.size();

        for (Double[] point : dataPoints) {
            double x = point[0];
            double y = point[1];
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumXX += x * x;
        }

        double xMean = sumX / n;
        double yMean = sumY / n;

        double ssXY = sumXY - n * xMean * yMean;
        double ssXX = sumXX - n * xMean * xMean;

        double b1 = ssXY / ssXX;
        double b0 = yMean - b1 * xMean;

        return new double[]{b0, b1};
    }*/
}
