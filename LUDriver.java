/**
 * Created by nick on 3/31/2015.
 */
public class LUDriver {
    public static void main(String[] args) {
        MatrixReader mread = new MatrixReader();
        FactoredMatrix mFact = mread.getM().lu_fact();
        System.out.println("LU Factorization");
        System.out.println("");
        System.out.println("L:");
        System.out.println(mFact.left);
        System.out.println("");
        System.out.println("U:");
        System.out.println(mFact.right);
        System.out.println("");
        System.out.println("error:");
        System.out.println(mFact.error);
    }
}
