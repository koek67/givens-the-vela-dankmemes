public class PowerMethodSolver {
    private Matrix a_old, x0_old, a, x0_new;
    private double tol;
    private double u_k1, u_k0;
    private int max_iter = 20000;
    private int k_max = 0;
    private boolean conv = false;

    public PowerMethodSolver(Matrix a) {
        Matrix b = new Matrix(a.getRows(), 1);
        // fill in with ones
        for (int i = 0; i < b.getRows(); i++) {
            for (int j = 0; j < b.getCols(); j++) {
                b.set(i, j, 1);
            }
        }
        this.a_old = a;
        this.x0_old = b;
        this.a = a_old.clone();
        x0_new = x0_old.clone();
        this.tol = 10E-08;
        u_k0 = 0;
        u_k1 = x0_old.norm();
    }

    public PowerMethodSolver(Matrix a, Matrix x0, double tol) {
        this.a_old = a;
        this.x0_old = x0;
        this.a = a_old.clone();
        x0_new = x0.clone();
        this.tol = tol;
        u_k0 = 0;
        u_k1 = x0.norm();
    }

    public void iterate() {
        for (int i = 0; i < max_iter; i++) {
            // compute Ax_k
            Matrix ax_k = Matrix.mult(a, x0_old);
            // System.out.println("A: " + a + "xk: \n" + x0_old);
            // System.out.println("Axk product:");
            // System.out.println(ax_k);
            // System.out.println("---");
            // find largest element of the matrix product
            double mu_k = ax_k.norm();
            //System.out.println("muk: " + mu_k + " old_k: " + u_k0);

            if (Math.abs(mu_k - u_k0) < tol) {
                conv = true;
                k_max = i + 1;
                // find largest
                for (int j = 0; j < ax_k.getRows(); j++) {
                    if (Math.abs(ax_k.get(j, 0)) == mu_k) {
                        u_k1 = ax_k.get(j, 0);
                    }
                }
                x0_old = ax_k.scale(1 / mu_k).clone();
                // System.out.println("GOT IT: " + (i+1));
                return;
            }

            x0_old = ax_k.scale(1 / mu_k).clone();
            // System.out.println("scaled: " + "\n" + x0_old);
            u_k0 = mu_k;
        }
    }

    public String toString() {
        String s = "";
        if (conv) {
            s += "Converged after " + k_max + " iterations.";
            s += "\n" + "Dominant eigenvalue: " + u_k1;
            s += "\n" + "Dominant eigenvector: \n" + x0_old;
        } else {
            s += "Did not converge after " + max_iter + " iterations.";
        }
        s += "\n";
    //    s += "" + a_old + "---- \n";
        return s;
    }

    public static void main(String[] args) {
        double[][] m1 = {
            {4, 5},
            {6, 5},
        };
        double[][] m2 = {
            {1},
            {1}
        };
        PowerMethodSolver js = new PowerMethodSolver(new Matrix(m1), new Matrix(m2), 10E-08);
        js.iterate();
        System.out.println(js);
    }


}



























// public void step1() {
//     x0_new = new Matrix(x0_old.getRows(), x0_old.getCols());
//     // calculate x0 with Ax
//     x0_new = Matrix.mult(a_old, x0_old);
//     // System.out.println("Ax: " + "\n" + x0);
//     u_k1 = x0_new.norm();
// }
