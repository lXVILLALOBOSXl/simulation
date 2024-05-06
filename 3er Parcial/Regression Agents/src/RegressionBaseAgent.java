import data_regress_utils.DataSet;
import data_regress_utils.RegressionModel;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class RegressionBaseAgent extends Agent {
    /*
        * Elements to be used for create the model with the choosed algorithm. EG:
        *  private double crossoverRate = 0.95;
     */
    private DataSet dataSet;

    protected void setup() {

        System.out.println("Agent "+ getLocalName() + " started.");

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("regression"); // Name of the service (what solves?)
        sd.setName("genetic-algorithm"); // Kind of algorithm to be used for the service
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }

        // addBehaviour(new GeneticAlgorithmAgent.offerRequestsServer()); // Offer service
        // addBehaviour(new GeneticAlgorithmAgent.modelOrdersServer()); // Accept service
    }


    private class offerRequestsServer extends CyclicBehaviour {

        @Override
        public void action() {

            // methods to be used for create the model with the choosed algorithm. EG:
            // recombinedPopulation.initialize();

            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);

            if (msg != null) {
                ACLMessage reply = msg.createReply();
                try {
                    if (msg.getContentObject() instanceof DataSet) {
                        dataSet = (DataSet) msg.getContentObject();
                        // methods to be used for response. EG:
                        // RegressionModel regressionModel = train();
                        reply.setPerformative(ACLMessage.PROPOSE);
                        // Response for offer request
                        // reply.setContent(regressionModel.getFitness(dataSet).toString());
                    } else {
                        reply.setPerformative(ACLMessage.REFUSE);
                        reply.setContent("unknown-action");
                    }
                    myAgent.send(reply);
                }catch (UnreadableException e){
                    e.printStackTrace();
                }

            } else {
                block();
            }
        }
    }

    private class modelOrdersServer extends CyclicBehaviour {
        @Override
        public void action() {


            // methods to be used for create the model with the choosed algorithm. EG:
            // recombinedPopulation.initialize();

            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                ACLMessage reply = msg.createReply();
                try {
                    if (msg.getContentObject() instanceof DataSet) {
                        try {
                            dataSet = (DataSet) msg.getContentObject();
                        } catch (UnreadableException e) {
                            e.printStackTrace();
                        }
                        // methods to be used for responsed accept. EG:
                        // RegressionModel regressionModel = train();
                        reply.setPerformative(ACLMessage.INFORM);
                        // Response for accept request
                        // reply.setContent(regressionModel.toString());
                    } else {
                        reply.setPerformative(ACLMessage.FAILURE);
                        reply.setContent("unknown-action");
                    }
                }catch (UnreadableException e){
                    e.printStackTrace();
                }
                myAgent.send(reply);
                reply.setPerformative(ACLMessage.INFORM);
            } else {
                block();
            }
        }
    }
}
