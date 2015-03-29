import java.util.ArrayList;
import java.util.*;
/**
 * ShiftRegister is a class that models the shift register. It will
 * create all nesessary Buckets and Pathways. A user can send the
 * shift register input and collect the output.
 */
public class ShiftRegister {

    private LinkedList<Integer> input;
    private ArrayList<Integer> out_top, out_bot;

    public ShiftRegister(int[] _input) {
        input = new LinkedList<Integer>();
        out_top = new ArrayList<Integer>();
        out_bot = new ArrayList<Integer>();
        for (int i : _input)
            input.add(i);
    }

    public void generate() {
        // set up initial stuff
        // buckets for the shift register
        LinkedList<Integer> buckets = new LinkedList<Integer>();
        // places to store output of the top and bottom taps
        ArrayList<Integer> top = new ArrayList<Integer>();
        ArrayList<Integer> bot = new ArrayList<Integer>();

        // for the first four elements in buckets, add to top and bot buffers
        ArrayList<Integer> buff_top = new ArrayList<Integer>();
        ArrayList<Integer> buff_bot = new ArrayList<Integer>();

        int newSize = (4 - input.size() % 4) + input.size() + 1;

        // interate until shiftNum
        for (int shiftNum = 0; shiftNum < newSize; shiftNum++) {
            // System.out.println("-----------------------");
            // add next num to bucket queue (to the front)
            if (shiftNum < input.size())
                buckets.addFirst(input.get(shiftNum));
            else buckets.addFirst(0);
            // System.out.println("shiftNum: " + shiftNum);
            // System.out.println("buckets: " + buckets);

            for (int i = 0; i < 4 && i < buckets.size(); i++) {
                if (buckets.get(i) != null) {
                    int elem = buckets.get(i).intValue();

                    if (i == 0) {
                        buff_top.add(elem);
                        buff_bot.add(elem);
                        // System.out.println(elem + " added to top");
                        // System.out.println(elem + " added to bot");
                    } else if (i == 1) {
                        buff_bot.add(elem);
                        // System.out.println(elem + " added to bot");
                    } else if (i == 2) {
                        buff_top.add(elem);
                        // System.out.println(elem + " added to top");
                    } else if (i == 3) {
                        buff_top.add(elem);
                        buff_bot.add(elem);
                        // System.out.println(elem + " added to top");
                        // System.out.println(elem + " added to bot");
                    }

                }
            }

            // xor all values int buff_top and buff_bot and add values to top and bot
            if (buff_top.size() > 0) {
                int value = buff_top.get(0);
                for (int i = 1; i < buff_top.size(); i++) {
                    value = value ^ buff_top.get(i);
                }
                // System.out.println("Result of top-xor: " + value);
                top.add(value);
            }
            buff_top.clear();

            if (buff_bot.size() > 0) {
                int value = buff_bot.get(0);
                for (int i = 1; i < buff_bot.size(); i++) {
                    value = value ^ buff_bot.get(i);
                }
                // System.out.println("Result of bot-xor:" + value);
                bot.add(value);
            }
            buff_bot.clear();
        }
        // System.out.println("---------- DONE ------------");
        // System.out.println(top);
        // System.out.println(bot);

        out_top = top;
        out_bot = bot;
    }

    public void jacobi() {
        jacobi_top();
        jacobi_bot();
    }

    public void jacobi_top() {
        // calculate coefficients of lin eq.
        for (int i = 0; i < out_top.size(); i++) {

        }
    }



    public void jacobi_bot() {}

    public int division(int dividend, int divisor) {
        boolean negative = false;

        if ((dividend & (1 << 31)) == (1 << 31)) { // Check for signed bit
            negative = !negative;
            dividend = add(~dividend, 1);  // Negation
        }

        if ((divisor & (1 << 31)) == (1 << 31)) {
            negative = !negative;
            divisor = add(~divisor, 1);  // Negation
        }

        int quotient = 0;
        long r;

        for (int i = 30; i >= 0; i = subtract(i, 1)) {
            r = (divisor << i);
           // Left shift divisor until it's smaller than dividend
            if (r < Integer.MAX_VALUE && r >= 0) { // Avoid cases where comparison between long and int doesn't make sense
                if (r <= dividend) {
                    quotient |= (1 << i);
                    dividend = subtract(dividend, (int) r);
                }
            }
        }
        if (negative) {
            quotient = add(~quotient, 1);
        }
        return quotient;
    }

    public int add(int a, int b) {

        int partialSum, carry;

        do {
            partialSum = a ^ b;
            carry = (a & b) << 1;

            a = partialSum;
            b = carry;

        } while (carry != 0);

        return partialSum;
    }

    public int subtract(int a, int b) {
        return add(a, add(~b, 1));
    }

    public void make_g(int[] data, int n) {
        // calc dimensions: data.length * (data.length + n)
        double[][] d = new double[n][data.length + n*2];
        Matrix g = new Matrix(d);
        // fill it in
        int shift = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < data.length; j++) {
                g.set(i,j + shift, data[j]);
            }
            shift += 2;
        }
        g.print();
    }

    public String toString() {
        System.out.println(out_top.size());
        System.out.println(out_bot.size());
        return out_top + "\n" + out_bot;
    }

    public static void main(String[] args) {
        int[] h = {1};
        System.out.println(h.length);
        ShiftRegister sr = new ShiftRegister(h);
        sr.generate();
        int[] g = {1,0,1,1,0};
        sr.make_g(g, h.length);
        System.out.println(sr);
        System.out.println(sr.division(1101,0100));
    }
}
