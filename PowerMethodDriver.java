import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by nick on 3/31/2015.
 */
public class PowerMethodDriver {
    public static void main(String... args) {
        MatrixReader input1 = new MatrixReader();
        Matrix m = input1.getM();
        System.out.println("Enter the tolerance");
        Scanner kb = new Scanner(System.in);
        double tol = kb.nextDouble();
        PowerMethodSolver js = new PowerMethodSolver(m, tol);
        js.iterate();
        System.out.println(js);
    }
}
