package examples.messaging;
 
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
 
public class AgenteReceptor extends Agent {
   
    protected void setup() {
        addBehaviour(new ReceptorComportamiento());
    }

   private class ReceptorComportamiento extends Behaviour {
            private boolean fin = false;
       
            public void action() {
                System.out.println(" Preparandose para recibir");
 
            //Obtiene el primer mensaje de la cola de mensajes
                ACLMessage mensaje = receive();
 
                if (mensaje!= null) {
                    System.out.println(getLocalName() + ": acaba de recibir el siguiente mensaje: ");
                    System.out.println(mensaje.toString());
                    fin = true;
                }else {
                    block(); // This will put the agent to sleep until a new message arrives
                }
            }

            public boolean done() {
                return fin;
            }

	  
   }
}
