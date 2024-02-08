package examples.behaviours;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class SLRAgent extends Agent {

  protected void setup() {
    addBehaviour(new SLRBehaviour());
  } 

  private class SLRBehaviour extends Behaviour {

    boolean end= false;

    public void action() {
       Regression mlr = new MultipleLinearRegression();
       LinearAlgebra la = new LinearAlgebra();
       HelperArithmetic ha = new HelperArithmetic();
       DataSet ds = new DataSet();
       
          
       mlr.regress(ds.getData(), la);
       
       System.out.println(slr.getB0());     
       System.out.println(slr.getB1());
       System.out.println("Regression Eq...");

       slr.predict(70);

       end = true;
    } 
    
    public boolean done() {
      if (end)
        return true;
      else
	return false;
    }
   
    public int onEnd() {
      myAgent.doDelete();
      return super.onEnd();
    } 
  }    // END of inner class ...Behaviour
}
