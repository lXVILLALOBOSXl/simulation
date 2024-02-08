package examples.behaviours;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

import java.util.Scanner;

public class OneShotAgent extends Agent {
  protected void setup() {
    System.out.println("Agent "+ getLocalName() + " started.");
    addBehaviour(new SimpleLinearRegresion());
  } 

  private class SimpleLinearRegresion extends OneShotBehaviour {

    public void action() {
      Scanner s = new Scanner(System.in);
      DataSet dataSet = new DataSet(
              new double[]{43.60,50.44,59.01,66.30,82.36,92.15,100.51,110.06,111.51},
              new double[]{ 1261.08,1475.28,1657.52,2059.05,2303.76,2512.64,2751.46,2787.67,2939.13}
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

    public int onEnd() {   
       myAgent.doDelete();
      System.out.println("Agent "+ myAgent.getLocalName() + " ended.");
       return super.onEnd();
    } 
  }    // END of inner class ...Behaviour
}  
