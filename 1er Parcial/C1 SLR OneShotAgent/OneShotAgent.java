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
              new double[]{23,26,30,34,43,48,52,57,58},
              new double[]{651,762,856,1063,1190,1298,1421,1440,1518}
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
