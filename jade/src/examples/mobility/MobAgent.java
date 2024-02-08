import jade.core.Agent;
import jade.core.Location;

public class MobAgent extends Agent {

    private static final long serialVersionUID = 1L;

    @Override
    protected void setup() {
        System.out.println("Hello World");
        jade.core.ContainerID contID = new jade.core.ContainerID("Main-Container", null);
        contID.setName("Main-Container");
        contID.setAddress("192.168.1.64");

	Location destination = contID;
	doMove(destination);

        super.setup();
    }

    @Override
    protected void beforeMove() {
        System.out.println("Preparing to move");
        super.beforeMove();
    }

    @Override
    protected void afterMove() {
        System.out.println("Arrived to destination");
        super.afterMove();
    }
}
