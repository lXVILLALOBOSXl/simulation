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

    //SLR
    private DataSet dataSet = new DataSet(
            new double[][]{{23}, {26}, {30}, {34}, {43}, {48}, {52}, {57}, {58}},
            new double[]{651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518}
    );

    //MLR
    /*private DataSet dataSet = new DataSet(
            new double[][]{
                    {2050, 0.23, 3711367, 0, 38.1, 2.24, 551, 53.5, 876613025, 16.84, 9735033990.0},
                    {2045, 0.35, 5585537, -414772, 36.6, 2.24, 545, 50.1, 811749463, 17.09, 9481803274.0},
                    {2040, 0.50, 7793541, -415736, 35.0, 2.24, 536, 46.7, 744380367, 17.31, 9198847240.0},
                    {2035, 0.66, 10016298, -415732, 33.3, 2.24, 523, 43.5, 675456367, 17.48, 8887524213.0},
                    {2030, 0.80, 11726140, -440124, 31.7, 2.24, 506, 40.4, 607341981, 17.59, 8548487400.0},
                    {2025, 0.97, 13386631, -469182, 30.1, 2.24, 487, 37.4, 541844847, 17.65, 8211020230.0},
                    {2020, 1.04, 13586631, -532687, 28.4, 2.24, 464, 34.5, 478806414, 17.70, 7794798739.0},
                    { 2019, 1.02, 13664177, -532687, 27.1, 2.24, 460, 34.0, 467853665, 17.71, 7713468100.0},
                    { 2018, 1.04, 13965495, -532687, 27.1, 2.24, 455, 33.6, 455919640, 17.74, 7631091040.0},
                    { 2017, 1.07, 14270898, -532687, 27.1, 2.24, 450, 33.2, 444058024, 17.86, 7547858925.0},
                    { 2016, 1.10, 14581602, -471005, 27.0, 2.40, 445, 32.7, 432806776, 17.75, 7464022049.0},
                    { 2015, 1.20, 15174247, -470015, 26.8, 2.40, 440, 32.3, 420668355, 17.84, 7379797139.0},
                    { 2010, 1.47, 17731430, -531169, 25.1, 2.80, 415, 30.1, 377170689, 17.31, 6956823603.0},
                    { 2005, 1.67, 18907788, -377841, 23.8, 3.14, 386, 27.6, 327611385, 16.84, 6541907027.0},
                    { 2000, 1.85, 19856170, -136514, 22.7, 3.48, 354, 27.6, 285353003, 16.87, 6143493823.0},
                    { 1995, 2.01, 20285191, 230314, 21.5, 3.82, 319, 25.5, 238396327, 16.99, 5744212979.0},
                    { 1990, 2.23, 21435816, 230314, 20.1, 4.26, 282, 23.0, 190321782, 16.87, 5327231061.0},
                    { 1985, 2.33, 21318809, 142961, 19.0, 4.82, 245, 20.1, 139372603, 15.99, 4870921740.0},
                    { 1980, 2.32, 19217774, 0, 18.2, 5.39, 208, 19.7, 116497000, 15.29, 4458003514.0},
                    { 1975, 2.33, 17407311, 0, 17.3, 5.41, 176, 19.7, 93053374, 14.86, 4079480606.0},
                    { 1970, 2.15, 14635527, 0, 19.7, 5.72, 139, 19.7, 74359010, 14.32, 3700437046.0},
                    { 1965, 2.07, 11635383, 0, 19.7, 5.89, 113, 19.7, 59049493, 13.97, 3339583597.0},
                    { 1960, 1.91, 8781733, 0, 19.7, 5.97, 89, 19.7, 44948060, 13.31, 3034949748.0},
                    { 1955, 1.72, 6591733, 0, 19.7, 6.07, 70, 19.7, 33688695, 12.95, 2773019936.0}
            },
            new double[]{
                    1639176033, 1620619200, 1592691513, 1553723810, 1503642322, 1458323780, 1400101776, 1380004385, 1366417754, 1352617328, 1338676785, 1324517249, 1234281170, 1147609927, 1059633675, 963922588, 873277798, 784360008, 698952844, 623102897, 548159652, 499123324, 450547679, 409880595
            }
    );*/

    //Polynomial Regression
/*    private DataSet dataSet = new DataSet(
            new double[][]{{1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}},
            new double[]{1, 4, 9, 16, 25, 36, 49, 64, 81}
    );*/


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