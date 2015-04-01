import java.util.ArrayList;

/**
 * Created by nick on 3/31/2015.
 */
public class FactorizationSolver {
    public static void main(String[] args) {
        while(true) {
            AugMatrixReader arem = new AugMatrixReader();
            Matrix m = arem.getM();

            ArrayList<Double> b = arem.getB().toVector();
            System.out.println();
            System.out.println("xsol for LU of input matrix");

            System.out.println(m.solve_lu_b(b));
            System.out.println();
            System.out.println("error for LU of input matrix");
            System.out.println(m.lu_fact().error);
            System.out.println();
            System.out.println("xsol for householders QR of input matrix");
            System.out.println(m.solve_qr_b_H(b));
            System.out.println();
            System.out.println("error for householders QR of input matrix");
            ArrayList<Double> sol = m.solve_qr_b_G(b);

            System.out.println(m.qr_fact_house().error);
            System.out.println();
            System.out.println("xsol for Givens QR of input matrix");
            System.out.println(sol);
            System.out.println();
            System.out.println("error for Givens QR of input matrix");
            System.out.println(m.qr_fact_givens().error);
            System.out.println();
            System.out.println("error for Givens Hxsol - b of input matrix");
            System.out.println(Matrix.norm(Matrix.subtract(Matrix.mult(m, sol), b)));

            System.out.println();
            System.out.println();
        }
    }
    
}
