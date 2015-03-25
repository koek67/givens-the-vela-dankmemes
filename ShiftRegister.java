import java.util.ArrayList;
import java.util.Arrays;
/**
 * ShiftRegister is a class that models the shift register. It will
 * create all nesessary Buckets and Pathways. A user can send the
 * shift register input and collect the output.
 */
public class ShiftRegister {

    public ShiftRegister() {}

    public void init(int[] input) {
        // Create xor buckets //
        XORBucket top = new XORBucket();
        XORBucket bot = new XORBucket();

        // first bucket
        Bucket b1 = new Bucket();
        // second bucket
        Bucket b2 = new Bucket();
        // third bucket
        Bucket b3 = new Bucket();

        // Create last pathway:
        // This pathway will exit into the xor buckets
        ArrayList<Bucket> p4_end = new ArrayList<Bucket>();
        p4_end.add(top);
        p4_end.add(bot);
        Pathway p4 = new Pathway(p4_end);
        b3.setPathway(p4);

        // Create 2nd-to-last pathway
        // This will go to the last bucket and send data to
        // the top xor bucket
        ArrayList<Bucket> p3_end = new ArrayList<Bucket>();
        p3_end.add(top);
        p3_end.add(b3);
        Pathway p3 = new Pathway(p3_end);
        b2.setPathway(p3);

        // Create 2nd pathway
        // This will go to the 2nd bucket and send data to
        // the bottom xor bucket
        ArrayList<Bucket> p2_end = new ArrayList<Bucket>();
        p2_end.add(bot);
        p2_end.add(b2);
        Pathway p2 = new Pathway(p2_end);
        b1.setPathway(p2);

        // Create 1st pathway
        // This will go to the 1st bucket and the top and bottom
        // xor buckets
        ArrayList<Bucket> p1_end = new ArrayList<Bucket>();
        p1_end.add(top);
        p1_end.add(bot);
        p1_end.add(b1);
        Pathway p1 = new Pathway(p1_end);

        // send the data
        for (int i : input) {
            p1.send(new Bit(i));
        }
        top.printDump();
        bot.printDump();
    }

    public static void main(String[] args) {
        ShiftRegister sr = new ShiftRegister();
        int[] h = {1, 0, 1, 1, 0};
        sr.init(h);
    }

}
