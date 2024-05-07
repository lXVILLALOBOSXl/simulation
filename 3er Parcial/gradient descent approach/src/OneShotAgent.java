import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;

public class OneShotAgent extends Agent {
  protected void setup() {
    System.out.println("Agent "+ getLocalName() + " started.");
    SequentialBehaviour sequential = new SequentialBehaviour();

    DataSet dataSet = new DataSet(
            new double[][]{{23}, {26}, {30}, {34}, {43}, {48}, {52}, {57}, {58}},
            new double[]{651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518}
    );



    double[] toPredict = new double[]{65,78,62};
    sequential.addSubBehaviour(new GradientDescendent(dataSet,70000, toPredict));

    /*dataSet = new DataSet(
            new double[]{
                    18,
                    22,
                    23,
                    26,
                    28,
                    31,
                    33,
            },
            new double[]{10000, 15000, 18000, 21000, 24000, 26500, 27000}
    );
    toPredict = new double[]{35,37,39};
    sequential.addSubBehaviour(new GradientDescendent(dataSet,70000, toPredict));

    dataSet = new DataSet(
            new double[]{1.2, 1.4, 1.6, 2.1, 2.3, 3.0,
                    3.1, 3.3, 3.3, 3.8, 4.0, 4.1,
                    4.1, 4.19, 4.6, 5.0, 5.19, 5.39,
                    6.0, 6.1, 6.89, 7.19, 8.0, 8.29,
                    8.79, 9.1, 9.6, 9.7, 10.4, 10.6},
            new double[]{39344.0, 46206.0, 37732.0, 43526.0, 39892.0, 56643.0,
                    1, 54446.0, 64446.0, 57190.0, 63219.0, 55795.0,
                    56958.0, 57082.0, 61112.0, 67939.0, 66030.0, 83089.0,
                    81364.0, 93941.0, 91739.0, 98274.0, 101303.0, 113813.0,
                    109432.0, 105583.0, 116970.0, 112636.0, 122392.0, 121873.0}
    );
    toPredict = new double[]{10.8,11.2,11.6};
    sequential.addSubBehaviour(new GradientDescendent(dataSet,70000, toPredict));*/

    sequential.addSubBehaviour(new OneShotBehaviour() {
      public void action() {
        System.out.println("All behaviors completed. Now terminating the agent.");
        myAgent.doDelete();
      }
    });

    addBehaviour(sequential);

  } 

  private class GradientDescendent extends OneShotBehaviour {
    private DataSet dataSet;

    private int epochs;

    private double[] toPredict;

    public GradientDescendent(DataSet dataSet, int epochs, double[] toPredict) {
      this.dataSet = dataSet;
      this.epochs = epochs;
      this.toPredict = toPredict;
    }

    public void action() {


      Gradient slr = new Gradient(dataSet);

      System.out.println(dataSet);
      slr.train(epochs);
      System.out.println("Equation: " + slr.equation());
      System.out.println("Determination Coefficient = " + slr.getDeterminationCoefficient());
      System.out.println("Correlation Coefficient = " + slr.getCorrelationCoefficient());

      for (double n :
              toPredict) {
        System.out.println("Predict for " + n + ": " + slr.predict(n));
      }

    }
  }    // END of inner class ...Behaviour
}  
