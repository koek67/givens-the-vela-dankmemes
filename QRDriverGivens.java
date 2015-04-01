/**
 * Created by nick on 3/31/2015.
 */
public class QRDriverGivens {
    public static void main(String[] args) {
        MatrixReader mread = new MatrixReader();
        FactoredMatrix mFact = mread.getM().qr_fact_givens();
        System.out.println("QR Factorization - Householder");
        System.out.println("");
        System.out.println("Q:");
        System.out.println(mFact.left);
        System.out.println("");
        System.out.println("R:");
        System.out.println(mFact.right);
        System.out.println("");
        System.out.println("error:");
        System.out.println(mFact.error);
    }
}
