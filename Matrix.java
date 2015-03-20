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

    /**
     * will create an empty 2D ArrayList
     */
    public Matrix() {
        this.backing = new ArrayList<ArrayList<Double>>();
        init();
    }

    /**
     * will comvert a 2D array into a 2D ArrayList
     */
    public Matrix(double[][] input) {
        rows = input.length;
        cols = input[0].length;
        this.backing = new ArrayList<ArrayList<Double>>();
        for (int i = 0; i < rows; i++) {
            backing.add(new ArrayList<Double>(cols));
        }
        int i = 0;
        for (ArrayList<Double> a : backing) {
            for (int j = 0; j < cols; j++) {
                a.add(input[i][j]);
            }
            i++;
        }
    }

    /**
     * helper method for no-args constructor
     */
    private void init() {
        for (ArrayList<Double> a : backing) {
            a = new ArrayList<Double>();
        }
    }

    // BEGIN GETTERS/SETTERS

    public int getRows(){return rows;}
    public int getCols(){return cols;}

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

    // END GETTERS/SETTERS

    /**
     * @return true if (i, j) is in bounds
     */
    public boolean isValid(int i, int j) {
        return (i >= 0 && i < rows) && (j >= 0 && j < cols);
    }

    /**
     * will print out the 2D arraylist to the console
     */
    public void print() {
        for (ArrayList<Double> a : backing) {
            System.out.println(a);
        }
    }

    // START THE GOOD STUFF

    /**
     * Will add this matrix to other.
     * This matrix will be modified to be the sum of the two matricies
     */
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

    /**
     * Will perform operation this - other.
     * This matrix will be modified to be the difference of the two matricies
     */
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

    /**
     * Modifies values to be a hilbert matrix.
     * TODO doesn't work. think i did something stupid
     */
    public void makeHilbert() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double value = 1.0 / ((i + 1.0) + (j + 1.0) - 1);
                set(i, j, value);
            }
        }
    }

    /**
     * Will perform an in-place rref() on the backing matrix
     */
    public void rref() {
        int lead = 0;
        for (int r = 0; r < rows; r++) {
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
            ArrayList<Double> tmp = backing.get(i);
            backing.set(i, backing.get(r));
            backing.set(r, tmp);
            double val = get(r, lead);
            for (int j = 0; j < cols; j++) {
                set(r, j, get(r, j) / val);
            }
            for (int a = 0; a < rows; a++) {
                if (a == r) {
                    continue;
                }
                val = get(a, lead);
                for (int j = 0; j < cols; j++) {
                    set(a, j, get(a, j) - val * get(r, j));
                }
            }
            lead++;
        }
    }

    public static void main(String[] args) {
        double[][] input = {
                            {6, 7, 8, 9},
                            {5, 4, 3, 7},
                            {7, 9, 9, 0}
                        };
        Matrix m = new Matrix(input);
        m.makeHilbert();
        m.print();
    }

}
