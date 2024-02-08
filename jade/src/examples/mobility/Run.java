import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;


public class Run {

    public Run() {
        Runtime rt = Runtime.instance();
        rt.setCloseVM(true);
        AgentContainer mc = rt.createMainContainer(new ProfileImpl());

        Profile p = new ProfileImpl();
        p.setParameter("container-name", "Foo");
        AgentContainer ac = rt.createAgentContainer(p);

        try{
            AgentController rma = mc.createNewAgent("rma", "jade.tools.rma.rma", null);
            rma.start();
            AgentController ma = mc.createNewAgent("MA", "MobAgent", null);
            ma.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Run();
    }
}
