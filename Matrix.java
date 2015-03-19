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

    public void print() {
        for (ArrayList<Double> a : backing) {
            System.out.println(a);
        }
    }

    // START THE GOOD STUFF
    public void add(Matrix other) {
        // check the sizes
        if (rows != other.getRows() || cols != other.getCols()) {
            throw new IllegalArgumentException("fix dimensions pl0x");
        }
        // now do the thing
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double sum = get(i, j) + other.get(i, j);
                set(i, j, sum);
            }
        }
    }

    public void subtract(Matrix other) {
        // check the sizes
        if (rows != other.getRows() || cols != other.getCols()) {
            throw new IllegalArgumentException("fix dimensions pl0x");
        }
        // now do the thing
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double diff = get(i, j) - other.get(i, j);
                set(i, j, diff);
            }
        }
    }

    public void makeHilbert() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double value = 1 / (i + j - 1);
                set(i, j, value);
            }
        }
    }

    public void rref() {
        double lead = 0;
        for (int r = 0; i < rows; r++) {
            if (cols <= lead) {
                return;
            }
            int i = r;
            while (get(i, lead) == 0) {
                i++;
                if (rows == i) {
                    i = r;
                    lead++;
                    if (cols == lead) {
                        return;
                    }
                }
            }
            ArrayList<Double> tmp = backing[i];
            backing[i] = backing[r];
            backing[r] = tmp;
            double val = get(r, lead);
            for (int j = 0; j < cols; j++) {
                set(r, j, get(r, j) / val);
            }
            for (int i = 0; i < rows; i++) {
                if (i == r) {
                    continue;
                }
                val = get(i, lead);
                for (int j = 0; j < cols; j++) {
                    set(i, j, get(i, j) - val * get(r, j));
                }
            }
            lead++;
        }
    }
}
