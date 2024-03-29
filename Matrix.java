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

    public String toStringInt() {
        String representation = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                representation += ((int) get(i,j)) % 2;
            }
            representation += "\n";
        }
        return representation;
    }

    /**
     * will convert a 2D array into a 2D ArrayList
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

    public Matrix(int n, int m) {
        this(genMatrix(n, m));
    }

    public static double[][] genMatrix(int n, int m) {
        double[][] arr = new double[n][m];
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < m; y++) {
                arr[x][y] = 0;
            }
        }
        return arr;
    }

    public double[][] toArray() {
        double[][] arr = new double[rows][cols];
        for (int x =0; x < rows; x++) {
            for (int y =0; y < cols; y++) {
                arr[x][y] = get(x, y);
            }
        }
        return arr;
    }
    /**
     * helper method for no-args constructor
     */
    private void init() {
        for (ArrayList<Double> a : backing) {
            a = new ArrayList<Double>();
        }
    }

    public static Matrix bad_inverse(Matrix m) {
        Matrix big = new Matrix(m.getRows(), m.getCols() * 2);
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getCols() * 2; j++) {
                if (j < m.getCols()) {
                    big.set(i, j, m.get(i, j));
                } else if (i == j - m.getCols()) {
                    big.set(i, j, 1);
                }
            }
        }
        big.rref();

        Matrix extract = new Matrix(m.getRows(), m.getCols());
        for (int i = 0; i < m.getRows(); i++) {
            int k = 0;
            for (int j = m.getCols(); j < big.getCols(); j++) {
                extract.set(i, k, big.get(i, j));
                k++;
            }
        }

        return extract;
    }





    // BEGIN GETTERS/SETTERS
    public int getRows(){return rows;}
    public int getCols(){return cols;}
    public ArrayList<Double> getRow(int x) {
        return backing.get(x);
    }
    public ArrayList<Double> getCol(int x) {
        ArrayList<Double> nums = new ArrayList<Double>();
        for (int y = 0; y < rows; y++) {
            nums.add(backing.get(y).get(x));
        }
        return nums;
    }

    public static double dotProduct(ArrayList<Double> v1, ArrayList<Double> v2) {
        double dotProd = 0;
        if (v1.size() != v2.size()) {
            throw new IllegalArgumentException("mismatched input lengths");
        }
        for(int x = 0; x < v1.size() && x < v2.size(); x++) {
            dotProd += v1.get(x) * v2.get(x);
        }
        return dotProd;
    }
    public static Matrix mult(Matrix m1, Matrix m2) {
        if(m1.cols != m2.rows) {
            throw new IllegalArgumentException("Matrices of improper dims");
        }
        Matrix prod = new Matrix(m1.rows, m2.cols);
        for (int x = 0; x < m1.rows; x++) {
            for (int y = 0; y < m2.cols; y++) {
                prod.set(x, y, dotProduct(m1.getRow(x), m2.getCol(y)));
            }
        }
        return prod;
    }

    public static Matrix transpose(Matrix m1) {
        Matrix trans = new Matrix(m1.cols, m1.rows);
        for (int x = 0; x < m1.rows; x++) {
            for (int y = 0; y < m1.cols; y++) {
                trans.set(y, x, m1.get(x, y));
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

    public String toString() {
        String representation = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                representation += String.format("%03.6f\t", get(i,j));
            }
            representation += "\n";
        }
        return representation;
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
        Matrix copy = new Matrix(this.toArray());
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

    public static boolean equals(Matrix a, Matrix b) {
        if (a.getRows() != b.getRows() || a.getCols() != b.getCols()) {
            return false;
        }
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < b.getRows(); j++) {
                if (a.get(i, j) != b.get(i, j)) {
                    return false;
                }
            }
        }
        return true;
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
    public ArrayList<Double> toVector() {
        if (rows != 1 && cols != 1) {
            throw new IllegalArgumentException("cannot toVector non vector matrices");
        }
        ArrayList<Double> arr = new ArrayList<>();
        for(int x = 0; x < rows || x < cols; x++) {
            if (rows > cols)
                arr.add(get(x,0));
            else
                arr.add((get(0,x)));
        }
        return arr;
    }


    public FactoredMatrix lu_fact() {
        Matrix L = new Matrix(rows, cols);
        for(int x =0; x < rows; x++) {
            L.set(x, x, 1);
        }
        Matrix U = this.clone();
        for (int y = 0; y < cols - 1; y++) {
            Matrix currL = new Matrix(rows, cols);
            for(int x =0; x < rows; x++) {
                currL.set(x, x, 1);
            }


            for (int x = y + 1; x < rows ; x++) {
                double currElim = U.get(y, x);
                double pivot = U.get(y, y);
                double val = currElim / pivot;
                L.set(x, y, val);
                currL.set(x, y, -val);
            }
            U = mult(currL, U);
        }
        Matrix LU = mult(L, U);

        LU.subtract(this);
        double error = LU.norm();
        FactoredMatrix fm = new FactoredMatrix(L, U, error);
        return fm;
    }




    public double norm() {
        double max = 0;
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                if (max < Math.abs(get(x, y))) {
                    max = Math.abs(get(x, y));
                }
            }
        }
        return max;
    }

    public static double norm(ArrayList<Double> arr) {
        return Math.sqrt(dotProduct(arr, arr));
    }


    public FactoredMatrix qr_fact_givens() {

        Matrix givens = new Matrix(rows, cols);
        double error;
        Matrix copy = this.clone();
        for (int x = 0; x < rows && x < cols; x++ ) {
            givens.set(x, x, 1);
        }

        for (int y = 0; y < cols - 1; y++) {
            for (int x = rows - 1; x > y ; x--) {
                Matrix temp = copy.givensRotate(y, x);

                copy = mult(temp, copy);


                givens = mult(givens, transpose(temp));

            }
        }
        Matrix QR = mult(givens, copy);
        QR.subtract(this);
        error = QR.norm();
        FactoredMatrix returnable = new FactoredMatrix(givens, copy, error);
        return returnable;
    }

    public Matrix givensRotate (int row, int col) {
        double x1 = get(row, row);
        double x2 = get(col, row);

        double cosx = x1 / Math.hypot(x1, x2);
        double sinx = -x2 / Math.hypot(x1, x2);
        Matrix givens = new Matrix(rows, cols);
        for (int x = 0; x < rows && x < cols; x++ ) {
            givens.set(x,x,1);
        }
        givens.set(row, col, -sinx);
        givens.set(col, row, sinx);
        givens.set(row, row, cosx);
        givens.set(col, col, cosx);
        return givens;
    }

    public FactoredMatrix qr_fact_house () {
        Matrix R = this.clone();
        Matrix Q = new Matrix(rows, cols);
        for (int x = 0; x < rows && x < cols; x++) {
            Q.set(x, x, 1);
        }
        for (int x = 0; x < cols - 1; x++) {
            Matrix temp = R.householderRotate(x);
            Q = mult(Q, temp);
            R = mult(temp, R);

        }

        Matrix QR = mult(Q, R);
        QR.subtract(this);
        double error = QR.norm();
        return new FactoredMatrix(Q, R , error);
    }

    public static double[][] toArray(ArrayList<Double> v1) {
        double[][] arr = new double[v1.size()][1];
        for (int x = 0; x < v1.size(); x++) {
            arr[x][0] = v1.get(x);
        }
        return arr;
    }


    public Matrix householderRotate (int col) {
        ArrayList<Double> curCol = new ArrayList<>(getCol(col).subList(col, cols));
        double normCol = norm(curCol);
        curCol.set(0, curCol.get(0) + Math.signum(curCol.get(0)) * normCol);
        Matrix u = new Matrix(toArray(curCol));
        Matrix ut = transpose(u);
        Matrix house = new Matrix(rows - col, cols - col);
        for (int x = 0; x < rows - col && x < cols - col; x++) {
            house.set(x, x, 1);
        }
        house.subtract(mult(u,ut).scale(2 / norm(curCol) / norm(curCol)));


        Matrix Q = new Matrix(rows, cols);
        for (int x = 0; x < rows && x < cols; x++) {
            Q.set(x, x, 1);
        }
        for(int x = col; x < rows; x++) {
            for( int y = col; y < cols; y++) {
                Q.set( x, y, house.get(x - col, y - col));
            }
        }

        return Q;
    }

    public Matrix scale(double scal) {
        Matrix scale = this.clone();
        for (int x = 0 ; x < rows; x++) {
            for( int y =0; y < cols; y++) {
                scale.set(x, y , scale.get(x, y) * scal);
            }
        }
        return scale;
    }

    public ArrayList<Double> solve_lu_b(ArrayList<Double> b) {
        ArrayList<Double> solved = new ArrayList<>();

        FactoredMatrix sol = lu_fact();
        Matrix L = sol.left, U = sol.right;
        ArrayList<Double> y = augSolverL(L, b);
        solved = augSolverU(U, y);
        return solved;
    }

    public ArrayList<Double> solve_qr_b_H(ArrayList<Double> b) {
        ArrayList<Double> solved = new ArrayList<>();

        FactoredMatrix sol = qr_fact_house();
        Matrix Q = sol.left, R = sol.right, Qt = transpose(Q);

        ArrayList<Double> y = mult(Qt, b);
        solved = augSolverU(R, y);
        return solved;
    }



    public ArrayList<Double> solve_qr_b_G(ArrayList<Double> b) {
        ArrayList<Double> solved = new ArrayList<>();

        FactoredMatrix sol = qr_fact_givens();
        Matrix Q = sol.left, R = sol.right, Qt = transpose(Q);

        ArrayList<Double> y = mult(Qt, b);
        solved = augSolverU(R, y);
        return solved;
    }



    public static ArrayList<Double> mult(Matrix a, ArrayList<Double> b) {
        ArrayList<Double> mults = new ArrayList<>();
        if (a.cols != b.size()) {
            throw new IllegalArgumentException("dims mismatch");
        }
        for (int x = 0; x < a.rows; x++) {
            mults.add(dotProduct(a.backing.get(x), b));
        }
        return mults;
    }

    public static ArrayList<Double> augSolverL(Matrix m, ArrayList<Double> b) {
        ArrayList<Double> xSolved = new ArrayList<>();

        for(int x = 0; x < m.cols; x++) {
            double sum = 0.0;
            for (int y = 0; y < xSolved.size(); y++){
                sum +=  m.get(x,y) * xSolved.get(y);
            }
            xSolved.add((b.get(x) - sum) / m.get(x,x) );
        }
        return xSolved;
    }

    public static ArrayList<Double> augSolverU(Matrix m, ArrayList<Double> b) {

        ArrayList<Double> xSolved = new ArrayList<>(b.size());
        for(int x =0; x < b.size(); x++) {
            xSolved.add(0.0);
        }
        for(int x = m.rows - 1; x >= 0; x--) {
            double sum = 0.0;
            for (int y = x + 1; y < m.cols ; y++){
                sum +=  m.get(x,y) * xSolved.get(y);
            }
            xSolved.set(x, (b.get(x) - sum) / m.get(x, x));
        }
        return xSolved;
    }
    public double maxNorm(ArrayList<Double> arr1) {
        double max = 0;
        for (int x = 0; x < arr1.size(); x++) {
            if (max < Math.abs(arr1.get(x))) max = Math.abs(arr1.get(x));
        }
        return max;
    }
    public static ArrayList<Double> subtract(ArrayList<Double> arr1, ArrayList<Double> arr2) {
        ArrayList<Double> sum = new ArrayList<>();
        for (int x = 0; x < arr1.size(); x++) {
            sum.add(arr1.get(x) - arr2.get(x));
        }
        return sum;
    }

    public static void main(String[] args) {
        double[][] input = {
                {1, .5, .333333, .25},
                {.5, .333333, .25, .2},
                {.333333, .25, .2, .166667},
                {.25, .2, .166667, .142857}
        };

        ArrayList<Double> input2 = new ArrayList<>();
        input2.add(0.0464159);
        input2.add(0.0464159);input2.add(0.0464159);
        input2.add(0.0464159);

        double[][] input3 = {
                {1},
                {.5}
        };
        Matrix m = new Matrix(input);
        System.out.print(m.solve_lu_b(input2));





    }

}
