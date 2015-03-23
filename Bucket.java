/**
 * A Bucket is a node in a pthaway that holds a bit.
 */
public class Bucket {

    protected Bit data;

    protected Pathway path;

    public Bucket(Pathway path) {
        this.path = path;
    }

    /**
     * Will send the current Bit over the pathway
     * and acquire the newData in its store.
     */
    public void shift(Bit newData) {
        // send current bit over the pathway
        if (data != null)
            pathway.send(data);
        // reset the data to new data
        data = newData;
    }


}
