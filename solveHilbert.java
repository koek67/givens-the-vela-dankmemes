import java.util.ArrayList;

/**
 * Created by nick on 3/31/2015.
 */
public class solveHilbert {
    public static void main(String... args) {
    for(int x = 2; x <= 20; x++) {
        Matrix m = new Matrix(x, x);
        m.makeHilbert();
        ArrayList<Double> b = new ArrayList<>();
        for (int y = 1; y <= x; y++) {
            b.add(Math.pow(.1, x / 3.0));
        }
        System.out.println();
        System.out.println("xsol for LU of " + x + "x" + x + " Hilbert");

        System.out.println(m.solve_lu_b(b));
        System.out.println();
        System.out.println("error for LU of " + x + "x" + x + " Hilbert");
        System.out.println(m.lu_fact().error);
        System.out.println();
        System.out.println("xsol for householders QR of " + x + "x" + x + " Hilbert");
        System.out.println(m.solve_qr_b_H(b));
        System.out.println();
        System.out.println("error for householders QR of " + x + "x" + x + " Hilbert");
        ArrayList<Double> sol = m.solve_qr_b_G(b);

        System.out.println(m.qr_fact_house().error);
        System.out.println();
        System.out.println("xsol for Givens QR of " + x + "x" + x + " Hilbert");
        System.out.println(sol);
        System.out.println();
        System.out.println("error for Givens QR of " + x + "x" + x + " Hilbert");
        System.out.println(m.qr_fact_givens().error);
        System.out.println();
        System.out.println("error for Givens Hxsol - b of " + x + "x" + x + " Hilbert");
        System.out.println(Matrix.norm(Matrix.subtract(Matrix.mult(m, sol), b)));

        System.out.println();
        System.out.println();
    }
    }
}
