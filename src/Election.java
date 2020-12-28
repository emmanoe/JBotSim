import io.jbotsim.core.Link;
import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import io.jbotsim.ui.JViewer;
import io.jbotsim.core.Message;
import io.jbotsim.core.Color;

public class Election extends Node {

    boolean ELECTED;

    @Override
    public void onStart() {
        sendAll(new Message(getID()));
    }

    @Override
    public void onMessage(Message otherID) {
        String[] message = otherID.toString().split(" ");
        if (Integer.parseInt(message[3]) > getID()) {
            sendAll(new Message(Integer.parseInt(message[3])));
        } else if (Integer.parseInt(message[3]) == getID())
            setColor(Color.RED);
        ELECTED = true;
    }

    public static void main(String[] args) {
        Topology tp = new Topology();
        tp.disableWireless();
        Node n0 = new Node();
        Node n1 = new Node();
        Node n2 = new Node();
        Node n3 = new Node();

        tp.addNode(100, 100, n0);
        tp.addNode(300, 100, n1);
        tp.addNode(100, 300, n2);
        tp.addNode(300, 300, n3);

        Link link01 = new Link(n0, n1);
        Link link13 = new Link(n1, n3);
        Link link32 = new Link(n3, n2);
        Link link20 = new Link(n2, n0);

        tp.addLink(link01);
        tp.addLink(link13);
        tp.addLink(link32);
        tp.addLink(link20);

        tp.setDefaultNodeModel(Election.class);
        tp.setTimeUnit(900);
        new JViewer(tp);
        tp.start();
    }
}
