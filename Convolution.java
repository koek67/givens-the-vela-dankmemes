import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.*;

public class Convolution {

    public static String generate_rand_stream(int n) {
        // generate a random input
        int[] input = new int[n];
        //int[] input = {1,0,1,1};
        Random numGen = new Random();
        for (int i = 0; i < input.length; i++) {
            input[i] = numGen.nextInt(200) % 2;
        }
        // create a Shift register object with the input
        ShiftRegister sr = new ShiftRegister(input);
        String r = "";
        r += "Input: " + Arrays.toString(input);
        r += "\t Output: " + sr.get_y();
        r += "\n " + Matrix.transpose(sr.get_y0()).toStringInt();
        r += "\n " + Matrix.transpose(sr.get_y1()).toStringInt();
        // r += "\n" + sr;
        r += "\n " + "A0";
        r += "\n" + sr.get_A0().toStringInt();
        r += "\n " + "A1";
        r += "\n" + sr.get_A1().toStringInt();
        return r;
    }

    public static void test(int iter, int n) {
        for (int i = 0; i < iter; i++) {
            // generate a random input
            int[] input = new int[n];
            //int[] input = {1,0,1,1};
            Random numGen = new Random();
            for (int j = 0; j < input.length; j++) {
                input[j] = numGen.nextInt(200) % 2;
            }
            // create a Shift register object with the input
            ShiftRegister sr = new ShiftRegister(input);
            Matrix a0 = sr.get_A0();
            Matrix a1 = sr.get_A1();
            System.out.println(a0);
            System.out.println(a1);
        }
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("Enter the length of the input, an integer n");
            Scanner kb = new Scanner(System.in);
            int input = kb.nextInt();
            test(1, input);
        }
    }


}
