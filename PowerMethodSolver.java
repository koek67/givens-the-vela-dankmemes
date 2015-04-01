public class PowerMethodSolver {
    private Matrix a_old, x0_old, a, x0;
    private double tol;


    public PowerMethodSolver(Matrix a) {
        Matrix b = new Matrix(a.getRows(), 1);
        // fill in with ones
        for (int i = 0; i < b.getRows(); i++) {
            for (int j = 0; j < b.getCols(); j++) {
                b.set(i, j, 1);
            }
        }
        this(a, b, 10E-08);
    }

    public PowerMethodSolver(Matrix a, Matrix x0, double tol) {
        this.a_old = a;
        this.x0_old = x0;
        a = a_old.clone();
        x0 = x0.clone();
        this.tol = tol;
    }

    public void step1() {
        x0 = new Matrix(x0_old.getRows(), x0_old.getCols());
        // calculate x0 with Ax
        x0 = Matrix.mult(a_old, x0_old);
    }



}
