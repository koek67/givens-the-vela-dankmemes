import java.util.*;
import java.util.Objects;

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
                a.add((double)input[i][j]);
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
                set(r, j, get(r, j) / val * 1.0);
            }
            for (int a = 0; a < rows; a++) {
                if (a == r) {
                    continue;
                }
                val = get(a, lead) * 1.0;
                for (int j = 0; j < cols; j++) {
                    set(a, j, get(a, j) - val * get(r, j) * 1.0);
                }
            }
            lead++;
        }
    }

    public T[] qr_fact_givens() {
        T[] returnable = (T[]) new Object[];
        Matrix givens = new Matrix();
        Double error = 0;
        ArrayList<ArrayList<Double>> copy = backing.clone();
        for (int x = 0; x < rows && x < cols; x++ ) {
            givens.set(x, x, 1);
        }
        double error;
        for (int x = 0; x < rows - 1; x++) {
            for (int y = cols; y > x; y--) {
                Matrix temp = givensRotate(x, y);
                backing = temp.mult(backing);
                givens = givens.mult(givens.transpose());
            }
        }
        returnable[0] = backing;
        returnable[1] = givens;
        returnable[2] = error;
        backing = copy;
    }
    public Matrix givensRotate (int row, int col) {
        double x1 = get(ind - 1, col);
        double x2 = get(ind, col);
        double cosx = x1 / Math.sqrt(x1 * x1 + x2 * x2);
        double sinx = -x2 / Math.sqrt(x1 * x1 + x2 * x2);
        Matrix givens = new Matrix();
        for (int x = 0; x < rows && x < cols; x++ ) {
            givens.set(x,x,1);
        }
        givens.set(row, col, sinx);
        givens.set(row - 1, col + 1, -sinx);
        givens.set(row, col + 1, cosx);
        givens.set(row - 1,col, cosx);
        return givens;
    }
    public String dankMemes() { return "Jet Fuel can't dank steel memes"; }

    public static void main(String[] args) {
        double[][] input = {
                            {1, 3, 4, 9},
                            {4, 4, 2, 7},
                            {3, 3, 2, 0}
                        };
        Matrix m = new Matrix(input);
        m.rref();
        m.print();
    }

}
