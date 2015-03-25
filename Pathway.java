import java.util.ArrayList;
/**
 * A Pathway object is a transistion in a control system.
 * Pathways can point to other pathways or other nodes.
 *
 * Each pathway object will have a list of nodes that bits will be
 * sent to when they travel across this pathway.
 *
 * Pathways can be created by defining a root node from where the
 * pathway will originate. Then set the pathway's endpoints. This is
 * where the bit will be sent to.
 */
public class Pathway {

    private ArrayList<Bucket> endpoints;

    public Pathway(ArrayList<Bucket> endpoints) {
        this.endpoints = endpoints;
    }

    /**
     * Will send a Bit to all of the endpoints of
     * this pathway.
     */
    public void send(Bit data) {
        // iterate through the endpoints and
        // send the Bit data
        for (Bucket b : endpoints) {
            b.shift(data);
        }
    }

}
