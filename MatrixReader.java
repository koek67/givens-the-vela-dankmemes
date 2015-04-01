import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by nick on 3/31/2015.
 */
public class MatrixReader {
    private Matrix m;

    public MatrixReader()
    {
        System.out.println("Enter the input vector or matrix data file ");
        Scanner keyboard = new Scanner(System.in);
        String filename = keyboard.nextLine();
        File file = new File(filename);
        Scanner inputFile = new Scanner(System.in);
        double[][] input = new double[0][0];

        try {
            inputFile = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int y = 0; inputFile.hasNext(); y++) {
            String lineOne = inputFile.nextLine();
            int counter = lineOne.split(" ").length;
            if(input.length == 0)
                input = new double[counter][counter];
            for (int x = 0; x < counter; x++) {
                input[y][x] = Double.parseDouble(lineOne.split(" ")[x]);
            }

        }
        this.m = new Matrix(input);

    }
    public Matrix getM() { return m; }
}
