import data_regress_utils.DataSet;
import data_regress_utils.RegressionModel;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.io.Serializable;

public class SolutionDeterminerAgent extends Agent {

    private DataSet dataSet = new DataSet(
            new double[][]{{23}, {26}, {30}, {34}, {43}, {48}, {52}, {57}, {58}},
            new double[]{651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518}
    );

    private AID[] regressionAgents;


    protected void setup() {
        System.out.println("Agent "+ getLocalName() + " determiner started.");


                addBehaviour(new OneShotBehaviour() {
                    @Override
                    public void action() {
                        System.out.println("Trying to find a solution to:" + dataSet.toString());
                        // Update the list of candidate agents
                        DFAgentDescription template = new DFAgentDescription();
                        ServiceDescription sd = new ServiceDescription();
                        sd.setType("regression");
                        template.addServices(sd);
                        try {
                            DFAgentDescription[] result = DFService.search(myAgent, template);
                            System.out.println("Found the following regression agents:");
                            regressionAgents = new AID[result.length];
                            for (int i = 0; i < result.length; ++i) {
                                regressionAgents[i] = result[i].getName();
                                System.out.println(regressionAgents[i].getName());
                            }
                        } catch (FIPAException fe) {
                            fe.printStackTrace();
                        }

                        // Perform the request
                        myAgent.addBehaviour(new RequestPerformer());
                    }
                });



    }

    protected void takeDown() {
        System.out.println("Agent "+ getLocalName() + " determiner terminated.");
    }

    private class RequestPerformer extends Behaviour {
        private AID bestAgent;
        private double bestFitness;
        private String bestModel;
        private int repliesCnt = 0;
        private MessageTemplate mt;
        private int step = 0;


        @Override
        public void action() {
            switch (step) {
                case 0:
                    // Send the cfp to all sellers
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                    // Send the dataset as argument to the regression agents
                    try {
                        cfp.setContentObject(dataSet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    for (AID regressionAgent : regressionAgents) {
                        cfp.addReceiver(regressionAgent);
                    }
                    cfp.setConversationId("regression");
                    cfp.setReplyWith("cfp"+System.currentTimeMillis());
                    myAgent.send(cfp);
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("regression"),
                            MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                    step = 1;
                    break;
                case 1:
                    // Receive all proposals/refusals from regression agents
                    ACLMessage reply = myAgent.receive(mt);
                    if (reply != null) {
                        if (reply.getPerformative() == ACLMessage.PROPOSE) {
                            double fitness = Double.parseDouble(reply.getContent());
                            if (bestAgent == null || fitness > bestFitness) {
                                bestFitness = fitness;
                                bestAgent = reply.getSender();
                            }
                        }
                        repliesCnt++;
                        if (repliesCnt >= regressionAgents.length) {
                            step = 2;
                        }
                    } else {
                        block();
                    }
                    break;

                case 2:
                    // Receive the model order reply
                    ACLMessage order = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                    order.addReceiver(bestAgent);
                    try {
                        order.setContentObject(dataSet);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    order.setConversationId("regression");
                    order.setReplyWith("order"+System.currentTimeMillis());
                    myAgent.send(order);
                    mt = MessageTemplate.and(MessageTemplate.MatchConversationId("regression"),
                            MessageTemplate.MatchInReplyTo(order.getReplyWith()));
                    step = 3;
                    break;

                case 3:
                    // Receive the model order reply
                    reply = myAgent.receive(mt);
                    if (reply != null) {
                        if (reply.getPerformative() == ACLMessage.INFORM) {
                            System.out.println("Agent "+reply.getSender().getName()+" has the best model");
                            bestModel = reply.getContent();
                            System.out.println("Model: " + bestModel);
                        } else {
                            System.out.println("Agent "+reply.getSender().getName()+" failed to train");
                        }
                        step = 4;
                    } else {
                        block();
                    }
                    break;
            }
        }

        @Override
        public boolean done() {
            return false;
        }
    }

}