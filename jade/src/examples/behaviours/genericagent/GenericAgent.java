package examples.behaviours;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

public class GenericAgent extends Agent {

  protected void setup() {
    System.out.println("Agent "+getLocalName()+" started.");
    addBehaviour(new MyBehaviour());
  } 

  private class MyBehaviour extends Behaviour {
    int cont=0;

    public void action() {
        System.out.println("Agent's action method is executed");
        cont+=1;
    } 
    
    public boolean done() {
      if (cont == 10)
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
