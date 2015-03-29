import java.util.ArrayList;
/**
 * XORBuckets are spcial kinds of buckets that have
 * buffers that, when full, will xor all the values
 * in the buffer and send the result to a vector
 * dump.
 *
 * XORBuckets therefore have a modified shift()
 * function in which the data is collected in
 * a buffer then sent to the vector dumb when the
 * buffer is full.
 */
public class XORBucket extends Bucket {

    private ArrayList<Bit> buffer;
    private ArrayList<Bit> dump;

    public XORBucket() {
        super(null);
        buffer = new ArrayList<Bit>();
        dump = new ArrayList<Bit>();
    }

    public void shift(Bit newData) {
        // // place old data into buffer
        // if (newData != null)
        //     buffer.add(newData);
        //
        // // proceed with buffer operations //
        //
        // // if buffer is full
        // if (buffer.size() == 3) {
        //     // xor all the values and add to dump
        //     dump();
        //     // clear the buffer
        //     buffer.clear();
        // }
        if (newData != null)
            buffer.add(newData);
        dump();
    }

    public void dump() {
        Bit result = buffer.get(0);

        for (int i = 1; i < buffer.size(); i++) {
            result = result.xor(buffer.get(1));
        }

        dump.add(result);
        buffer.clear();
    }

    public void printDump() {
        System.out.println(dump);
    }

}
