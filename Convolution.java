import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.*;

public class Convolution {

    public static String generate_rand_stream(int n) {
        // generate a random input
        int[] input = new int[n];
        //int[] input = {1,0,1,1};
        Matrix in = new Matrix(n, 1);
        Random numGen = new Random();
        for (int i = 0; i < input.length; i++) {
            input[i] = numGen.nextInt(200) % 2;
        }
        // create a Shift register object with the input
        ShiftRegister sr = new ShiftRegister(input);
        String r = "";
        r += "Input: " + Arrays.toString(input);
        r += "\t Output: " + sr.get_y();
        // r += "\n" + sr;
        r += "\n " + "A0";
        r += "\n" + sr.get_A0().toStringInt();
        r += "\n " + "A1";
        r += "\n" + sr.get_A1().toStringInt();
        return r;
    }

<<<<<<< HEAD
    public static void decode(Matrix in) {
        int l = in.getRows();
        Matrix y0 = new Matrix(l / 2, 1);
        Matrix y1 = new Matrix(l / 2, 1);
        for (int i = 0; i < l; i++) {
            if (i % 2 == 0)
                y0.set(i, 0, in.get(0, i));
            else y1.set(i, 0, in.get(0, i));
        }
        ShiftRegister sr = new ShiftRegister(new int[l / 2]);
        Matrix a0 = sr.get_A0();
        Matrix a1 = sr.get_A1();

        System.out.println("Jacobi");
        JacobiSolver js = new JacobiSolver(a0, y0, new Matrix(new double[l / 2][1]), 10e-08);
        js.iterate();
        System.out.println(js);

        js = new JacobiSolver(a1, y1, new Matrix(new double[l / 2][1]), 10e-08);
        js.iterate();
        System.out.println(js);

        System.out.println("Gauss-Seidel");
        GaussSeidelSolver gs = new GaussSeidelSolver(a0, y0, new Matrix(new double[l / 2][1]), 10e-08);
        gs.iterate();
        System.out.println(gs);

        gs = new GaussSeidelSolver(a1, y1, new Matrix(new double[l / 2][1]), 10e-08);
        gs.iterate();
        System.out.println(gs);
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("Enter the length of the input, an integer n");
            Scanner kb = new Scanner(System.in);
            int input = kb.nextInt();
            generate_rand_stream(1, input);
        }
    }


}
