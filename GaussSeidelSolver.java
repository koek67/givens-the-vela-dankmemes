import java.util.*;

public class GaussSeidelSolver {

    private Matrix a, a_old, b, b_old, out, new_out;
    private int max_iter, k_max;
    private double tol;
    private boolean conv;

    public GaussSeidelSolver(Matrix a, Matrix b, Matrix x0) {
        this(a, b, x0, 200, 10E-08);
    }

    public GaussSeidelSolver(Matrix a, Matrix b, Matrix x0, int max_iter, double tol) {
        this.a = a;
        this.a_old = a.clone();
        this.b = b;
        this.b_old = b.clone();
        this.out = x0;
        this.max_iter = max_iter;
        this.tol = tol;
        new_out = out.clone();
        k_max = 0;
        conv = false;
    }

    public void iterate() {
        for (int i = 0; i < max_iter; i++) {
            step1();

            Matrix tmp = new_out.clone();
            tmp.subtract(out);
            if (Math.abs(tmp.norm()) < tol) {
                k_max = i + 1;
                conv = true;
                return;
            } else {
                out = new_out;
            }
        }
    }

    public void step1() {
        new_out = new Matrix(out.getRows(), out.getCols());
        for (int i = 0; i < a.getRows(); i++) {
            double sigma = 0;
            for (int j = 0; j < a.getRows(); j++) {
                if (j != i) {
                    sigma = sigma + a.get(i, j) * out.get(j, 0);
                }
            }
            double val = (1 / a.get(i, i)) * (b.get(i, 0) - sigma);
            new_out.set(i, 0, val);
        }
    }

    public String toString() {
        String s = "";
        if (conv) {
            s += "Converged after " + k_max + " iterations.";
        } else {
            s += "Did not converge after " + max_iter + " iterations.";
        }
        s += "\n";
        s += "" + a_old + "" + "---- \n" + b_old + "---- \n" + new_out + "---- \n";
        return s;
    }

    public Matrix getA() { return a_old; }
    public Matrix getb() { return b_old; }
    public Matrix getX() { return new_out; }
    public boolean getConv() { return conv; }
    public static void main(String[] args) {
        Random numGen = new Random();
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
        GaussSeidelSolver js = new GaussSeidelSolver(new Matrix(m1), new Matrix(m2), new Matrix(m3));
        js.iterate();
        //System.out.println(js);


        for (int k = 0; k < 100; k++) {
            // choose random dimension
            int r = numGen.nextInt(20) + 1;
            // create the matricies
            Matrix a = new Matrix(r, r);
            Matrix b = new Matrix(r, 1);
            Matrix x = new Matrix(r, 1);
            // fill the matricies
            for (int i = 0; i < a.getRows(); i++) {
                for (int j = 0; j < a.getCols(); j++) {
                    a.set(i, j, 10*numGen.nextDouble() + 1.);
                }
            }

            for (int i = 0; i < r; i++) {
                b.set(i, 0, 40*numGen.nextDouble() + 1.);
                x.set(i, 0, 0);
            }
            js = new GaussSeidelSolver(a, b, x);
            js.iterate();
            boolean done = Matrix.equals(Matrix.mult(js.getA(), js.getX()), js.getb());
            if (!done && js.getConv()) {
                System.out.println(js);
                return;
            }
        }
        System.out.println("done");
    }

}
