import java.util.Scanner;

/**
 * Created by nick on 3/31/2015.
 */
public class gauss_seidel {
    public static void main(String... args) {
        AugMatrixReader input1 = new AugMatrixReader();
        Matrix m = input1.getM();
        Matrix b = input1.getB();
        MatrixReader input2 = new MatrixReader();
        Matrix x = input2.getM();
        System.out.println("Enter the tolerance");
        Scanner kb = new Scanner(System.in);
        double tol = kb.nextDouble();
        GaussSeidelSolver gs = new GaussSeidelSolver(m, b, x, 5000, tol);
        System.out.println(gs);
    }
}
