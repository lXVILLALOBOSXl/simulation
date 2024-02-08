package clips.agents;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

import net.sf.clipsrules.jni.*;

public class ClipsAgent extends Agent {

  Environment clips;
  
  protected void setup() { 
    clips = new Environment();
    addBehaviour(new TellBehaviour());
    addBehaviour(new AskBehaviour());
  } 

  private class TellBehaviour extends Behaviour {

    boolean tellDone = false;

    public void action() {
       System.out.println("Agent's tell was executed");
     
       clips.eval("(clear)");
    
       clips.build("(defrule r1 (sintoma ?x) => (printout t ?x crlf))"); 
       clips.eval("(reset)");

       clips.eval("(assert (sintoma a))");
       clips.eval("(assert (sintoma b))");

       clips.eval("(facts)");

       clips.run();

       tellDone = true;
    } 
    
    public boolean done() {
      if (tellDone)
        return true;
      else
	return false;
    }
   
  }    // END of inner class ...Behaviour

  private class AskBehaviour extends Behaviour {

    boolean askDone = false;

    public void action() {

       clips.run();

       System.out.println("Agent's ask was executed");
       askDone = true;
    } 
    
    public boolean done() {
      if (askDone)
        return true;
      else
	return false;
    }
   
    public int onEnd() {
      myAgent.doDelete();
      return super.onEnd();
    } 
  }  
}
