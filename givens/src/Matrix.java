import java.lang.Double;
import java.lang.IllegalArgumentException;
import java.util.*;
import java.util.ArrayList;
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
    public ArrayList<Double> getCol(int x) {
        return backing.get(x);
    }
    public ArrayList<Double> getRow(int x) {
        ArrayList<Double> nums = new ArrayList<Double>();
        for (int y = 0; y < cols; y++) {
            nums.add(backing.get(y).get(x));
        }
        return nums;
    }

    public static double dotProduct(ArrayList<Double> v1, ArrayList<Double> v2) {
        double dotProd = 0;
        for(int x = 0; x < v1.size() && x < v2.size(); x++) {
            dotProd += v1.get(x) * v2.get(x);
        }
        return dotProd;
    }
    public static Matrix mult(Matrix m1, Matrix m2) {
        if(m1.cols != m2.rows) {
            throw new IllegalArgumentException("Matrices of improper dims");
        }
        Matrix prod = new Matrix();
        for (int x = 0; x < m1.rows; x++) {
            for (int y = 0; y < m2.cols; y++) {
                prod.set(x, y, dotProduct(m1.getRow(x), m2.getCol(y)));
            }
        }
        return prod;
    }

    public static Matrix transpose(Matrix m1) {
        Matrix trans = new Matrix();
        for (int x = 0; x < m1.rows / 2; x++) {
            for (int y = 0; y < m1.cols / 2; y++) {
                double temp = m1.get(x,y);
                m1.set(x,y, m1.get(y, x));
                m1.set(y, x, temp);
            }
        }
        return trans;
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

    public Matrix clone() {
        Matrix copy = new Matrix();
        for (int x = 0; x < rows; x++) {
            for (int y =0; y < cols; y++) {
                copy.set(x, y, get(x, y));
            }
        }
        return copy;
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

    public FactoredMatrix qr_fact_givens() {

        Matrix givens = new Matrix();
        double error = 0;
        Matrix copy = this.clone();
        for (int x = 0; x < rows && x < cols; x++ ) {
            givens.set(x, x, 1);
        }
        for (int x = rows - 1; x >= 0; x--) {
            for (int y = cols; y > x; y--) {
                Matrix temp = givensRotate(x, y);
                copy = mult(temp, copy);
                givens = mult(givens, transpose(givens));
            }
        }
        Matrix QR = mult(givens, copy);
        QR.subtract(this);
        error = QR.norm();
        FactoredMatrix returnable = new FactoredMatrix(givens, copy, error);
        return returnable;
    }

    public double norm() {
        double max = 0;
        for (int x = 0; x < rows; x++) {
            for (int y =0; y < cols; y++) {
                if (max < get (x,y)) {
                    max = get(x, y);
                }
            }
        }
        return max;
    }


    public Matrix givensRotate (int row, int col) {
        double x1 = get(row - 1, col);
        double x2 = get(row, col);
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
