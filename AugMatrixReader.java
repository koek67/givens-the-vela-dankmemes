import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by nick on 3/31/2015.
 */
public class AugMatrixReader {
    private Matrix m, b;

    public AugMatrixReader()
    {
        System.out.println("Enter the input Augmented matrix A|b");
        Scanner keyboard = new Scanner(System.in);
        String filename = keyboard.nextLine();
        File file = new File(filename);
        Scanner inputFile = new Scanner(System.in);
        double[][] input = new double[0][0];
        double[][] b = new double[0][0];
        try {
            inputFile = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int y = 0; inputFile.hasNext(); y++) {
            String lineOne = inputFile.nextLine();
            int counter = lineOne.split(" ").length;
            if(input.length == 0)
                input = new double[counter - 1][counter - 1];
            if(b.length == 0)
                b = new double[counter - 1][1];
            for (int x = 1; x < counter; x++) {
                input[y][x - 1] = Double.parseDouble(lineOne.split(" ")[x - 1]);
            }
            b[y][0] = Double.parseDouble(lineOne.split(" ")[counter - 1]);
        }
        this.m = new Matrix(input);
        this.b = new Matrix(b);

    }
    public Matrix getM() { return m; }
    public Matrix getB() { return b; }
}
