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
        generate();
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

    /**
     * using the input, create A0
     */
    public Matrix get_A0() {
        int[] data = {1, 0, 1, 1};
        return make_g(data, input.size());
    }

    public Matrix get_A1() {
        int[] data = {1, 1, 0, 1};
        return make_g(data, input.size());
    }

    //11,01,01,01,00,00,10,01,00,00,01,11,11,01,10,11,00

    public Matrix get_y0() {
        Matrix a0 = get_A0();
        Matrix g = new Matrix(1, input.size());
        for (int i = 0; i < input.size(); i++)
            g.set(0, i, input.get(i));

        return Matrix.mult(Matrix.transpose(a0), Matrix.transpose(g));
    }

    public Matrix get_y1() {
        Matrix a0 = get_A1();
        Matrix g = new Matrix(1, input.size());
        for (int i = 0; i < input.size(); i++)
            g.set(0, i, input.get(i));

        return Matrix.mult(Matrix.transpose(a0), Matrix.transpose(g));
    }

    public ArrayList<String> get_y() {
        // multiplex the output
        Matrix y0 = get_y0();
        Matrix y1 = get_y1();
        // System.out.println(y0.getRows());
        // System.out.println(y1.getRows());
        Matrix y = new Matrix(y0.getRows(), 1);
        ArrayList<String> y_out = new ArrayList<String>();
        //System.out.println(y.getRows());
        for (int i = 0; i < y0.getRows(); i++) {
            // System.out.println((int)y0.get(1, i));
            // System.out.println((int)y1.get(1));
            String multi = ((int)(y0.get(i, 0)))%2 + "" + ((int)y1.get(i, 0))%2 + "";
            // System.out.print(multi + " ");
            y_out.add(multi);
        }
        return y_out;
    }

    public Matrix make_g(int[] data, int n) {
        // calc dimensions: data.length * (data.length + n)
        double[][] d = new double[n][data.length + n-1];
        Matrix g = new Matrix(d);
        // fill it in
        int shift = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < data.length; j++) {
                g.set(i,j + shift, data[j]);
            }
            shift += 1;
        }
        return g;
    }

    public String toString() {
        return out_top + "\n" + out_bot;
    }
}
