import java.util.*;
/**
 * functions to implement:
 * - add 2 matricies
 * - multply 2 matricies
 * - rref
 * - subtract 2 matricies
 * - transpose matrix
 * - determinent
 * - inverse
 */
public class Matrix {

    private ArrayList<ArrayList<Double>> backing;
    private int rows, cols;

    public Matrix() {
        this.backing = new ArrayList<ArrayList<Double>>();
        init();
    }

    public Matrix(double[][] mat) {

    }

    private void init() {
        for (ArrayList<Double> a : backing) {
            a = new ArrayList<Double>();
        }
    }

    public int getRows(){return rows;}
    public int getCols(){return cols;}

    public boolean isValid(int i, int j) {
        return (i >= 0 && i < rows) && (j >= 0 && j < cols);
    }

    public double get(int i, int j) {
        if (!isValid(i, j)) {
            throw new IllegalArgumentException("out of bounds pl0x");
        }
        return backing.get(i).get(j).doubleValue();
    }

    public void set(int i, int j, double value) {
        if (!isValid(i, j)) {
            throw new IllegalArgumentException("out of bounds pl0x");
        }
        backing.get(i).set(j, value);
    }

    // START THE GOOD STUFF
    public void add(Matrix other) {
        // check the sizes
        if (rows != other.getRows() || cols != other.getCols()) {
            throw new IllegalArgumentException("fix dimensions pl0x");
        }
    }
}
