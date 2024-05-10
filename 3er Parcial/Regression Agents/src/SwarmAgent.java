import data_regress_utils.DataSet;
import data_regress_utils.MultipleRegressionModel;
import data_regress_utils.PolynomialRegressionModel;
import data_regress_utils.RegressionModel;
import genetic_algorithm.RandomGenerator;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import particle_swarm.Particle;

public class SwarmAgent extends Agent {
    private int numberOfParticles = 20;
    private double cognitiveWeight = 0.9;
    private double socialWeight = 0.9;
    private double inertiaWeight = 0.9;
    private Particle[] swarm  = new Particle[numberOfParticles];;
    private Particle teamBest;
    private DataSet dataSet;

    private Double[][] ranges = new Double[][]{{1.0, 200.0}, {0.0, 50.0}};
    //private Double[][] ranges = new Double[][]{{0.0, 0.1}, {0.0, 0.1}, {0.0, 2.0}};

    protected void setup() {

        System.out.println("Agent "+ getLocalName() + " started.");

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("regression");
        sd.setName("particle-swarm");
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new SwarmAgent.offerRequestsServer());
        addBehaviour(new SwarmAgent.modelOrdersServer());
    }


    private class offerRequestsServer extends CyclicBehaviour {

        @Override
        public void action() {

            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
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
                        RegressionModel regressionModel = train();
                        reply.setPerformative(ACLMessage.PROPOSE);
                        reply.setContent(regressionModel.getFitness(dataSet).toString());
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
                        RegressionModel regressionModel = train();
                        reply.setPerformative(ACLMessage.INFORM);
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

    private RegressionModel train() {
        initializeSwarm();
        teamBest = getTeamBest();
        while (teamBest.getFitness() < 0.95){
            for (Particle particle : swarm) {
                particle.move(inertiaWeight, cognitiveWeight, socialWeight, teamBest);
                particle.setFitness(dataSet);
            }
            teamBest = getTeamBest();
        }
        return new MultipleRegressionModel(teamBest.getCoordinates());

    }

    private Particle getTeamBest() {
        Particle teamBest = swarm[0];
        for (Particle particle : swarm) {
            if (particle.getFitness() > teamBest.getFitness()) {
                teamBest = particle;
            }
        }
        return teamBest;
    }

    private void initializeSwarm() {
        for (int i = 0; i < swarm.length; i++) {
            double[] coordinates = new double[dataSet.getNumberOfFeatures()+1];
            for (int j = 0; j < dataSet.getNumberOfFeatures()+1; j++) {
                coordinates[j] = RandomGenerator.getRandomDouble(ranges[j][0], ranges[j][1]);
            }
            Particle p = new Particle(coordinates);
            p.setFitness(dataSet);
            swarm[i] = p;
        }
    }
}
