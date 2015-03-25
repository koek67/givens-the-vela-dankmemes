import java.util.ArrayList;
public class Vector {

    private ArrayList<Double> data;

    public Vector(){
        data = new ArrayList<Double>();
    }

    public Vector(int size) {
        data = new ArrayList<Double>(size);
    }

    public Vector(double[] input) {
        super();
        for (double d : input) {
            data.add(d);
        }
    }

    public void add(double in) {
        data.add(in);
    }

    public int size() {
        return data.size();
    }

    public double remove(int i) {
        return data.remove(i);
    }

    public double get(int i) {
        return data.get(i);
    }

    public String toString() {
        return data.toString();
    }

    public void addToFront(double d) {
        data.add(0, d);
    }

}
