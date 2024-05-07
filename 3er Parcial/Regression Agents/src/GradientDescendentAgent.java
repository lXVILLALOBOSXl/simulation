import data_regress_utils.DataSet;
import data_regress_utils.SimpleLinearRegression;
import formula.DiscreetMath;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

public class GradientDescendentAgent extends Agent {
    /*
     * Elements to be used for create the model with the choosed algorithm. EG:
     *  private double crossoverRate = 0.95;
     */
    private DataSet dataSet;
    private int epochs = 70000;
    private double learningRate = 0.0005;

    protected void setup() {

        System.out.println("Agent "+ getLocalName() + " started.");

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("regression"); // Name of the service (what solves?)
        sd.setName("gradient-descendent"); // Kind of algorithm to be used for the service
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new GradientDescendentAgent.offerRequestsServer()); // Offer service
        addBehaviour(new GradientDescendentAgent.modelOrdersServer()); // Accept service
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
        int epoch = 0;
        double[] beta = new double[2];
        beta[0] = 0;
        beta[1] = 0;
        while (epoch < epochs){
            double newB0 = beta[0] - (this.learningRate * this.calculateIntersect(beta));
            double newB1 = beta[1] - (this.learningRate * this.calculateSlope(beta));
            beta[0] = newB0;
            beta[1] = newB1;
            epoch+=1;
        }
        return new SimpleLinearRegression(beta);
    }

    private double calculateIntersect(double[] beta){
        double sum = 0;
        double n = this.dataSet.getN();

        for (int i = 0; i < n; i++){
            sum += (this.dataSet.getY()[i] - (beta[0] + (beta[1]*this.dataSet.getX()[i][0])));
        }
        return (-2/n) * sum;
    }

    private double calculateSlope(double[] beta){
        double sum = 0;
        double n = this.dataSet.getN();

        for (int i = 0; i < n; i++){
            sum += (this.dataSet.getX()[i][0] * (this.dataSet.getY()[i] - (beta[0] + (beta[1]*this.dataSet.getX()[i][0]))));
        }
        return (-2/n) * sum;
    }


}
