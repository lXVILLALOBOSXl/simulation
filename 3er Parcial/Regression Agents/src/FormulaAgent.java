import data_regress_utils.*;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import formula.DiscreetMath;

public class FormulaAgent extends Agent {
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
        sd.setName("formula"); // Kind of algorithm to be used for the service
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new FormulaAgent.offerRequestsServer()); // Offer service
        addBehaviour(new FormulaAgent.modelOrdersServer()); // Accept service
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
                        double[][] xValues = dataSet.getX();
                        boolean hasMoreThanOneElement = false;
                        for (double[] x : xValues) {
                            if (x.length > 1) {
                                hasMoreThanOneElement = true;
                                break;
                            }
                        }
                        if (hasMoreThanOneElement) {
                            reply.setPerformative(ACLMessage.REFUSE);
                            reply.setContent("unknown-action");
                        } else {
                            SimpleLinearRegression regressionModel = train();
                            reply.setPerformative(ACLMessage.PROPOSE);
                            reply.setContent(regressionModel.getFitness(dataSet).toString());
                        }
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
                        dataSet = (DataSet) msg.getContentObject();;
                        // methods to be used for responsed accept. EG:
                        SimpleLinearRegression regressionModel = train();
                        reply.setPerformative(ACLMessage.INFORM);
                        // Response for accept request
                        reply.setContent(regressionModel.toString());
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

    private SimpleLinearRegression train() {
        double b1 = calculateSlope();
        double b0 = calculateIntersect(b1);
        double[] betas = new double[]{b0, b1};
        return new SimpleLinearRegression(betas);
    }
    private double calculateIntersect(double b1) { // b0
        double[] x = new double[dataSet.getN()];
        int index = 0;
        for (double[] value : dataSet.getX()) {
            x[index++] = value[0];
        }
        return (DiscreetMath.sum(dataSet.getY()) - (b1 * DiscreetMath.sum(x))) / dataSet.getN();
    }

    private double calculateSlope() { // b1
        double[] x = new double[dataSet.getN()];
        int index = 0;
        for (double[] value : dataSet.getX()) {
            x[index++] = value[0];
        }
        return ((dataSet.getN() * DiscreetMath.sumMultiplication(x, dataSet.getY())) - (DiscreetMath.sum(x) * DiscreetMath.sum(dataSet.getY()))) / ((dataSet.getN() * DiscreetMath.sumPow(x, 2)) - (Math.pow(DiscreetMath.sum(x), 2)));
    }


}
