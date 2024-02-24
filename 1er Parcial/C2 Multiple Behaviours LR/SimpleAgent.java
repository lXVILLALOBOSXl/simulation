package examples.behaviours;

import examples.behaviours.com.data.DataSet;
import examples.behaviours.com.maths.DataAnalysisUtils;
import examples.behaviours.com.maths.MultipleLinearRegresion;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;

import java.util.Arrays;


public class SimpleAgent extends Agent {

    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started.");

        // Add OneShotBehaviour
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                DataSet dataSetAge = new DataSet(
                        new double[][]{
                                {1, 18},
                                {1, 22},
                                {1, 23},
                                {1, 26},
                                {1, 28},
                                {1, 31},
                                {1, 33}
                        },
                        new double[]{10000, 15000, 18000, 21000, 24000, 26500, 27000}

                );

                MultipleLinearRegresion mlr = new MultipleLinearRegresion(dataSetAge);
                System.out.println("\n" + mlr.equation());
                System.out.println("Correlation Coefficient = " + DataAnalysisUtils.calculateCorrelationCoefficient(dataSetAge));
                System.out.println("Determination Coefficient = " + DataAnalysisUtils.calculateDeterminationCoefficient(dataSetAge));
                double[] toPredict = new double[]{35.0};
                System.out.println("Prediction for " + Arrays.toString(toPredict) + ": " + mlr.predict(toPredict));
                toPredict = new double[]{37.0};
                System.out.println("Prediction for " + Arrays.toString(toPredict) + ": " + mlr.predict(toPredict));
                toPredict = new double[]{39.0};
                System.out.println("Prediction for " + Arrays.toString(toPredict) + ": " + mlr.predict(toPredict));
            }
        });

        // Add SimpleBehaviour
        addBehaviour(new SimpleBehaviour() {
            @Override
            public void action() {
                DataSet dataSetSalary = new DataSet(
                        new double[][]{
                                {1, 1.2}, {1, 1.4}, {1, 1.6}, {1, 2.1}, {1, 2.3}, {1, 3.0},
                                {1, 3.1}, {1, 3.3}, {1, 3.3}, {1, 3.8}, {1, 4.0}, {1, 4.1},
                                {1, 4.1}, {1, 4.19}, {1, 4.6}, {1, 5.0}, {1, 5.19}, {1, 5.39},
                                {1, 6.0}, {1, 6.1}, {1, 6.89}, {1, 7.19}, {1, 8.0}, {1, 8.29},
                                {1, 8.79}, {1, 9.1}, {1, 9.6}, {1, 9.7}, {1, 10.4}, {1, 10.6}
                        },
                        new double[]{
                                39344.0, 46206.0, 37732.0, 43526.0, 39892.0, 56643.0,
                                1, 54446.0, 64446.0, 57190.0, 63219.0, 55795.0,
                                56958.0, 57082.0, 61112.0, 67939.0, 66030.0, 83089.0,
                                81364.0, 93941.0, 91739.0, 98274.0, 101303.0, 113813.0,
                                109432.0, 105583.0, 116970.0, 112636.0, 122392.0, 121873.0
                        }

                );

                MultipleLinearRegresion mlr = new MultipleLinearRegresion(dataSetSalary);
                System.out.println("\n" + mlr.equation());
                System.out.println("Correlation Coefficient = " + DataAnalysisUtils.calculateCorrelationCoefficient(dataSetSalary));
                System.out.println("Determination Coefficient = " + DataAnalysisUtils.calculateDeterminationCoefficient(dataSetSalary));
                double[] toPredict = new double[]{10.8};
                System.out.println("Prediction for " + Arrays.toString(toPredict) + ": " + mlr.predict(toPredict));
                toPredict = new double[]{11.1};
                System.out.println("Prediction for " + Arrays.toString(toPredict) + ": " + mlr.predict(toPredict));
                toPredict = new double[]{11.4};
                System.out.println("Prediction for " + Arrays.toString(toPredict) + ": " + mlr.predict(toPredict));
            }

            @Override
            public boolean done() {
                return true;
            }
        });

        // Add GenericBehaviour
        addBehaviour(new SimpleLinearRegressionBehaviour());


    }

    /**
     * Inner class SimpleLinearRegressionBehaviour
     */
    private class SimpleLinearRegressionBehaviour extends Behaviour {

        public void action() {
            DataSet dataSetPopulation = new DataSet(
                    new double[][]{
                            {1, 2050, 0.23, 3711367, 0, 38.1, 2.24, 551, 53.5, 876613025, 16.84, 9735033990.0},
                            {1, 2045, 0.35, 5585537, -414772, 36.6, 2.24, 545, 50.1, 811749463, 17.09, 9481803274.0},
                            {1, 2040, 0.50, 7793541, -415736, 35.0, 2.24, 536, 46.7, 744380367, 17.31, 9198847240.0},
                            {1, 2035, 0.66, 10016298, -415732, 33.3, 2.24, 523, 43.5, 675456367, 17.48, 8887524213.0},
                            {1, 2030, 0.80, 11726140, -440124, 31.7, 2.24, 506, 40.4, 607341981, 17.59, 8548487400.0},
                            {1, 2025, 0.97, 13386631, -469182, 30.1, 2.24, 487, 37.4, 541844847, 17.65, 8211020230.0},
                            {1, 2020, 1.04, 13586631, -532687, 28.4, 2.24, 464, 34.5, 478806414, 17.70, 7794798739.0},
                            {1, 2019, 1.02, 13664177, -532687, 27.1, 2.24, 460, 34.0, 467853665, 17.71, 7713468100.0},
                            {1, 2018, 1.04, 13965495, -532687, 27.1, 2.24, 455, 33.6, 455919640, 17.74, 7631091040.0},
                            {1, 2017, 1.07, 14270898, -532687, 27.1, 2.24, 450, 33.2, 444058024, 17.86, 7547858925.0},
                            {1, 2016, 1.10, 14581602, -471005, 27.0, 2.40, 445, 32.7, 432806776, 17.75, 7464022049.0},
                            {1, 2015, 1.20, 15174247, -470015, 26.8, 2.40, 440, 32.3, 420668355, 17.84, 7379797139.0},
                            {1, 2010, 1.47, 17731430, -531169, 25.1, 2.80, 415, 30.1, 377170689, 17.31, 6956823603.0},
                            {1, 2005, 1.67, 18907788, -377841, 23.8, 3.14, 386, 27.6, 327611385, 16.84, 6541907027.0},
                            {1, 2000, 1.85, 19856170, -136514, 22.7, 3.48, 354, 27.6, 285353003, 16.87, 6143493823.0},
                            {1, 1995, 2.01, 20285191, 230314, 21.5, 3.82, 319, 25.5, 238396327, 16.99, 5744212979.0},
                            {1, 1990, 2.23, 21435816, 230314, 20.1, 4.26, 282, 23.0, 190321782, 16.87, 5327231061.0},
                            {1, 1985, 2.33, 21318809, 142961, 19.0, 4.82, 245, 20.1, 139372603, 15.99, 4870921740.0},
                            {1, 1980, 2.32, 19217774, 0, 18.2, 5.39, 208, 19.7, 116497000, 15.29, 4458003514.0},
                            {1, 1975, 2.33, 17407311, 0, 17.3, 5.41, 176, 19.7, 93053374, 14.86, 4079480606.0},
                            {1, 1970, 2.15, 14635527, 0, 19.7, 5.72, 139, 19.7, 74359010, 14.32, 3700437046.0},
                            {1, 1965, 2.07, 11635383, 0, 19.7, 5.89, 113, 19.7, 59049493, 13.97, 3339583597.0},
                            {1, 1960, 1.91, 8781733, 0, 19.7, 5.97, 89, 19.7, 44948060, 13.31, 3034949748.0},
                            {1, 1955, 1.72, 6591733, 0, 19.7, 6.07, 70, 19.7, 33688695, 12.95, 2773019936.0}
                    },
                    new double[]{
                            1639176033, 1620619200, 1592691513, 1553723810, 1503642322, 1458323780, 1400101776, 1380004385, 1366417754, 1352617328, 1338676785, 1324517249, 1234281170, 1147609927, 1059633675, 963922588, 873277798, 784360008, 698952844, 623102897, 548159652, 499123324, 450547679, 409880595
                    }
            );

            MultipleLinearRegresion mlr = new MultipleLinearRegresion(dataSetPopulation);
            System.out.println("\n" + mlr.equation());
            System.out.println("Correlation Coefficient = " + DataAnalysisUtils.calculateCorrelationCoefficient(dataSetPopulation));
            System.out.println("Determination Coefficient = " + DataAnalysisUtils.calculateDeterminationCoefficient(dataSetPopulation));
            double[] toPredict = new double[]{1, 2060, 0.15, 2111367, 0, 45.1, 2.24, 562, 56.7, 926613025, 16.87, 10235033990.0};
            System.out.println("Prediction for " + Arrays.toString(toPredict) + ": " + mlr.predict(toPredict));
            toPredict = new double[]{1, 2070, 0.14, 2411367, -532687, 47.1, 2.24, 568, 58.7, 966613025, 17.87, 10335033990.0};
            System.out.println("Prediction for " + Arrays.toString(toPredict) + ": " + mlr.predict(toPredict));
            toPredict = new double[]{1, 2080, 0.14, 2711367, -471005, 49.1, 2.24, 572, 60.7, 996613025, 16.87, 10435033990.0};
            System.out.println("Prediction for " + Arrays.toString(toPredict) + ": " + mlr.predict(toPredict));
        }

        public boolean done() {
            return true;
        }

        public int onEnd() {
            myAgent.doDelete();
            return super.onEnd();
        }
    }    // END of inner class SimpleLinearRegressionBehaviour


}




