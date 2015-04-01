import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by nick on 3/31/2015.
 */
public class jacobi {
    public static void main(String... args) {
        AugMatrixReader input1 = new AugMatrixReader();
        Matrix m = input1.getM();
        Matrix b = input1.getB();
        System.out.println(m);
        System.out.println(b);
        MatrixReader input2 = new MatrixReader();
        Matrix x = input2.getM();
        System.out.println("Enter the tolerance");
        Scanner kb = new Scanner(System.in);
        double tol = kb.nextDouble();
        JacobiSolver js = new JacobiSolver(m, b, x, 5000, tol);
        System.out.println(js);
    }
}
