public class JacobiSolver {

    private Matrix a;
    private Matrix b;
    private Matrix out;
    private int max_iter;
    private double tol;
    private Matrix new_out;
    private int k_max;
    private boolean conv;

    public JacobiSolver(Matrix a, Matrix b, Matrix x0) {
        this(a, b, x0, 200, 10E-08);
    }

    public JacobiSolver(Matrix a, Matrix b, Matrix x0, int max_iter, double tol) {
        this.a = a;
        this.b = b;
        this.out = x0;
        this.max_iter = max_iter;
        this.tol = tol;
        k_max = 0;
        conv = false;
    }

    public void iterate() {
        // Matrix d = get_d_inv();
        // Matrix r = get_r();
        int i = 0;
        for (i = 0; i < max_iter; i++) {
            step1();
            // check convergence
            Matrix tmp = new_out.clone();
            tmp.subtract(out);
            if (Math.abs(tmp.norm()) < tol) {
                out = new_out;
                k_max = i + 1;
                conv = true;
                return;
            } else {
                out = new_out;
            }
        }
    }

    public void step1() {
        // for each element in out
        new_out = new Matrix(out.getRows(), out.getCols());
        for (int i = 0; i < out.getRows(); i++) {
            double sigma = 0;
            for (int j = 0; j < out.getRows(); j++) {
                if (i != j) {
                    sigma += (a.get(i, j) * out.get(j, 0));
                }
            }
            double val = (b.get(i, 0) - sigma) / a.get(i, i);
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
        s += "" + a+ "" + "---- \n" + b + "---- \n" + out + "---- \n";
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
        JacobiSolver js = new JacobiSolver(new Matrix(m1), new Matrix(m2), new Matrix(m3));
        js.iterate();
        System.out.println(js);
    }

}
