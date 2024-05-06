import data_regress_utils.DataAnalysisUtils;
import data_regress_utils.DataSet;
import data_regress_utils.MultipleRegressionModel;
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
import matrix.LinearAlgebra;

public class MatrixAgent extends Agent{
    /*
     * Elements to be used for create the model with the choosed algorithm. EG:
     *  private double crossoverRate = 0.95;
     */
    private DataSet dataSet;
    private DataSet originalDataSet;
    private int grade = 1;

    protected void setup() {

        System.out.println("Agent "+ getLocalName() + " started.");

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("regression"); // Name of the service (what solves?)
        sd.setName("matrix"); // Kind of algorithm to be used for the service
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new MatrixAgent.offerRequestsServer()); // Offer service
        addBehaviour(new MatrixAgent.modelOrdersServer()); // Accept service
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
                        originalDataSet = (DataSet) msg.getContentObject();
                        dataSet = transformDataSet(originalDataSet, grade);
                        // methods to be used for response. EG:
                        MultipleRegressionModel regressionModel = train();
                        reply.setPerformative(ACLMessage.PROPOSE);
                        // Response for offer request
                        reply.setContent(regressionModel.getFitness(originalDataSet).toString());
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
                        originalDataSet = (DataSet) msg.getContentObject();
                        dataSet = transformDataSet(originalDataSet, grade);
                        // methods to be used for responsed accept. EG:
                        RegressionModel regressionModel = train();
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

    private DataSet transformDataSet(DataSet ds, int grade) {
        double[][] x = ds.getX();
        double[][] transformedX = new double[x.length][];

        for (int i = 0; i < x.length; i++) {
            transformedX[i] = new double[grade + 1];
            transformedX[i][0] = 1; // Add 1 to every array in x
            for (int j = 1; j <= grade; j++) {
                transformedX[i][j] = Math.pow(x[i][0], j); // Add the pow functions until n specified
            }
        }

        return new DataSet(transformedX, ds.getY());
    }

    private MultipleRegressionModel train() {
        double[] betas = LinearAlgebra.matrixVectorMultiplication(LinearAlgebra.inverseMatrix(LinearAlgebra.matrixMultiplication((LinearAlgebra.transposeMatrix(dataSet.getX())),dataSet.getX())),
                LinearAlgebra.matrixVectorMultiplication((LinearAlgebra.transposeMatrix(dataSet.getX())),dataSet.getY()));
        return new MultipleRegressionModel(betas);
    }
}
