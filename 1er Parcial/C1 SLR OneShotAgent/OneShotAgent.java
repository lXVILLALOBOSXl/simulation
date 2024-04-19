package examples.behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

import java.util.Scanner;

public class OneShotAgent extends Agent {
  protected void setup() {
    System.out.println("Agent "+ getLocalName() + " started.");
    DataSet dataSet = new DataSet(
            new double[]{23,26,30,34,43,48,52,57,58},
            new double[]{651,762,856,1063,1190,1298,1421,1440,1518}
    );
    addBehaviour(new SimpleLinearRegresion(dataSet,65));
  } 

  private class SimpleLinearRegresion extends OneShotBehaviour {
    private DataSet dataSet;

    private double toPredict;

    public SimpleLinearRegresion(DataSet dataSet, double toPredict) {
      this.dataSet = dataSet;
      this.toPredict = toPredict;
    }

    public void action() {
      SLR slr = new SLR(dataSet);

      System.out.println(dataSet);
      System.out.println("b0: " + slr.getB0());
      System.out.println("b1: " + slr.getB1());
      System.out.println("Equation: " + slr.getEquation());
      System.out.print("Invest to predict: ");
      System.out.println("Predict for " + toPredict + ": " + slr.predict(toPredict));
    }

    public int onEnd() {   
       myAgent.doDelete();
      System.out.println("Agent "+ myAgent.getLocalName() + " ended.");
       return super.onEnd();
    } 
  }    // END of inner class ...Behaviour
}  
