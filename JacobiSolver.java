public class JacobiSolver {

    private Matrix a;
    private Matrix b;
    private Matrix out;
    public JacobiSolver(Matrix a, Matrix b) {
        this.a = a;
        this.b = b;
        out = b.clone();
    }

    public void iterate() {
        for (int i = 0; i < 40; i++) {
            step();
            System.out.println("----------------------------");
        }
    }

    public void step() {
        // for each element in out
        Matrix new_out = new Matrix(out.getRows(), out.getCols());
        for (int i = 0; i < out.getRows(); i++) {
            double sigma = 0;
            for (int j = 0; j < out.getRows(); j++) {
                if (i != j) {
                    sigma = sigma + (a.get(i, j) * out.get(j, 0));
                }
            }
            double val = (b.get(i, 0) - sigma) / a.get(i, i);
            System.out.println(a.get(i,i) + "    " + b.get(i,0) + "   " + sigma + "    " + val);
            new_out.set(i, 0, val);
            sigma = 0;
        }
        out = new_out;
    }

    public double getSum(int i) {
        double val = 0;
        for (int j = 0; j < a.getCols(); j++) {
            if (i != j) {
                double a_i = a.get(i, j);
                double x_i = out.get(j, 0);
                val += a_i*x_i;
            }
        }
        return val;
    }

    public String toString() {
        String s = "";
        s += "\n";
        s += "" + a + "" + "---- \n" + b + "---- \n" + out + "---- \n";
        return s;
    }

    public static void main(String[] args) {
        double[][] m1 = {
            {2,3,4,5},
            {3,4,5,6},
            {4,5,6,7},
            {5,6,7,8}
        };
        double[][] m2 = {
            {1},
            {1},
            {1},
            {1}
        };
        JacobiSolver js = new JacobiSolver(new Matrix(m1), new Matrix(m2));
        js.iterate();
        System.out.println(js);
    }

}
