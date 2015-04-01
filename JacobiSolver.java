import java.util.*;
public class JacobiSolver {

    public static final int MAX_ITERATIONS = 200;
    private double[][] M;
    private double tol;
    private Matrix out;
    private int k_max;
    private boolean conv = true;

    public JacobiSolver(Matrix a, Matrix b, Matrix x0, double tol) {
        Matrix m0 = new Matrix(a.getRows(), a.getCols() + 1);
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < a.getCols(); j++) {
                m0.set(i, j, a.get(i, j));
            }
        }
        for (int i = 0; i < b.getRows(); i++) {
            m0.set(i, a.getCols(), b.get(i, 0));
        }
        M = m0.toArray();
        this.tol = tol;
        out = new Matrix(b.getRows(), 1);
    }

    public JacobiSolver(double [][] matrix) { M = matrix; }

    public void iterate() {
        int iterations = 0;
        int n = M.length;
        double epsilon = tol;
        double[] X = new double[n];
        double[] P = new double[n];
        double[] C = new double[n];
        Arrays.fill(X, 0);
        Arrays.fill(P, 0);
        while (true) {
            for (int t = 0; t < n; t++)
                C[t] = X[t];
            for (int i = 0; i < n; i++) {
                double sum = M[i][n];
                for (int j = 0; j < n; j++)
                    if (j != i)
                        sum -= M[i][j] * P[j];
                X[i] = 1/M[i][i] * sum;
            }
            iterations++;
            if (iterations == 1) continue;
            boolean stop = true;
            for (int i = 0; i < n && stop; i++)
                if (Math.abs(X[i] - P[i]) > epsilon)
                    stop = false;

            if (iterations == MAX_ITERATIONS) {
                conv = false;
                break;
            }

            if (stop || iterations == MAX_ITERATIONS) {
                break;
            }
            P = (double[])X.clone();
        }
        out = new Matrix(C.length, 1);
        for (int u = 0; u < C.length; u++) {
            out.set(u, 0, C[u]);
        }
        k_max = iterations;
    }

    public String toString() {
        String s = "";
        if (!conv) {
            s += "Did not converge after 200 iterations";
        }
        else {
            s += out.toString();
            s += "\n";
            s += "No. of iterations: " + k_max;
        }
        return s;
    }

    public static void main(String[] args) {
        double[][] m1 = {
            {5, -2, 3},
            {-3, 9, 1},
            {2, -1, -7},
        };
        double[][] m2 = {
            {-1},
            {2},
            {3}
        };

        double[][] m3 = {
            {0},
            {0},
            {0}
        };
        JacobiSolver js = new JacobiSolver(new Matrix(m1), new Matrix(m2), new Matrix(m3), 10E-08);
        js.iterate();
        System.out.println(js);
    }
}























// public class JacobiSolver {
//
//     private Matrix a;
//     private Matrix b;
//     private Matrix out;
//     private int max_iter;
//     private double tol;
//     private Matrix new_out;
//     private int k_max;
//     private boolean conv;
//
//     public JacobiSolver(Matrix a, Matrix b, Matrix x0) {
//         this(a, b, x0, 200, 0);
//     }
//
//     public JacobiSolver(Matrix a, Matrix b, Matrix x0, int max_iter, double tol) {
//         this.a = a;
//         this.b = b;
//         this.out = x0;
//         this.max_iter = max_iter;
//         this.tol = tol;
//         k_max = 0;
//         conv = false;
//     }
//
//     public void iterate() {
//         // Matrix d = get_d_inv();
//         // Matrix r = get_r();
//         int i = 0;
//         for (i = 0; i < max_iter; i++) {
//             step1();
//             // check convergence
//             Matrix tmp = new_out.clone();
//             tmp.subtract(out);
//             if (Math.abs(tmp.norm()) < tol) {
//                 out = new_out;
//                 k_max = i + 1;
//                 conv = true;
//                 return;
//             } else {
//                 out = new_out;
//             }
//         }
//     }
//
//     public void step1() {
//         // for each element in out
//         new_out = new Matrix(out.getRows(), out.getCols());
//         for (int i = 0; i < a.getRows(); i++) {
//             double sigma = 0;
//             for (int j = 0; j < a.getCols(); j++) {
//                 if (i != j) {
//                     sigma += (a.get(i, j) * out.get(j, 0));
//                 }
//             }
//             double val = (b.get(i, 0) - sigma) / a.get(i, i);
//             new_out.set(i, 0, val);
//         }
//     }
//
//     public String toString() {
//         String s = "";
//         if (conv) {
//             s += "Converged after " + k_max + " iterations.";
//         } else {
//             s += "Did not converge after " + max_iter + " iterations.";
//         }
//         s += "\n";
//         s += "" + a+ "" + "---- \n" + b + "---- \n" + out + "---- \n";
//         s += "\n";
//         s += Matrix.mult(a, out);
//         return s;
//     }
//
//     public Matrix get_out() { return new_out; }
//     public boolean get_conv() { return conv; }
//
//
//     public static void main(String[] args) {
//         double[][] m1 = {
//             {5, -2, 3},
//             {-3, 9, 1},
//             {2, -1, -7},
//         };
//         double[][] m2 = {
//             {-1},
//             {2},
//             {3}
//         };
//
//         double[][] m3 = {
//             {0},
//             {0},
//             {0}
//         };
//         JacobiSolver js = new JacobiSolver(new Matrix(m1), new Matrix(m2), new Matrix(m3));
//         js.iterate();
//         System.out.println(js);
//     }
//
// }
