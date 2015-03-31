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
            System.out.println(generate_rand_stream(n));
        }
    }

    public static void main(String[] args) {
        test(20, 5);
    }


}
