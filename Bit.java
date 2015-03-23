public class Bit {
    private int value;

    public static int ONE = 1;
    public static int ZERO = 0;

    public Bit (int b) {
        this.value = b;
    }

    public int value() { return value; }

    public Bit xor(Bit other) {
        int xor = value ^ other.value();
        return new Bit(xor);
    }
}
