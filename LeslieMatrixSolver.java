public class LeslieMatrixSolver {

    private Matrix leslie, pop_old, pop;
    private int start_year;
    private int curr_year;

    public LeslieMatrixSolver(Matrix leslie, Matrix _pop, int start_year) {
        this.leslie = leslie;
        this.pop_old = _pop;
        this.pop = pop_old.clone();
        this.start_year = start_year;
        curr_year = start_year;
    }

    public void reset() {
        pop = pop_old.clone();
        curr_year = start_year;
    }

    public void next_year() {
        pop = Matrix.mult(leslie, pop);
    }

    public void get_year(int year) {
        for (int i = 0; i < (year - start_year) % 10 + 1; i++) {
            this.pop = Matrix.mult(leslie, pop);
            curr_year+=10;
        }
    }

    public double get_curr_total_pop() {
        double m = 0;
        for (int i = 0; i < pop.getRows(); i++) {
            for (int j = 0; j < pop.getCols(); j++) {
                m += pop.get(i, j);
            }
        }
        return m;
    }

    public double get_old_total_pop() {
        double m = 0;
        for (int i = 0; i < pop_old.getRows(); i++) {
            for (int j = 0; j < pop_old.getCols(); j++) {
                m += pop_old.get(i, j);
            }
        }
        return m;
    }

    public Matrix getLeslie() { return leslie; }

    public static Matrix get_std_leslie() {
        double[][] les =
                            {
                            { 0, 1.2, 1.1, .9, .1,   0,  0,    0, 0},
                            {.7,   0,   0,  0,  0,   0,  0,    0, 0},
                            { 0, .85,   0,  0,  0,   0,  0,    0, 0},
                            { 0,   0,  .9,  0,  0,   0,  0,    0, 0},
                            { 0,   0,   0, .9,  0,   0,  0,    0, 0},
                            { 0,   0,   0,  0, .88,  0,  0,    0, 0},
                            { 0,   0,   0,  0,   0, .8,  0,    0, 0},
                            { 0,   0,   0,  0,   0,   0,.77,   0, 0},
                            { 0,   0,   0,  0,   0,   0,  0, .40, 0}
                            };
        return new Matrix(les);
    }

    public static Matrix get_std_pop() {
        double[][] p = {{2.1},
                        {1.9},
                        {1.8},
                        {2.1},
                        {2.0},
                        {1.7},
                        {1.2},
                        {0.9},
                        {0.5}};
        Matrix std_pop = new Matrix(p);
        return std_pop.scale(10E05);
    }

    public String toString() {
        String s = "";
        // s += "Original year: " + start_year + "\n";
        // s += "Original Population: " + get_old_total_pop() + "\n" + Matrix.transpose(pop_old);
        s += "Current year: " + curr_year + "\n";
        s += "Current Population: " + get_curr_total_pop() + "\n" + Matrix.transpose(pop);
        s += "Percent change: " + ((get_curr_total_pop() - get_old_total_pop()) / get_old_total_pop()) * 100;
        return s;
    }

    public static void main(String[] args) {
        LeslieMatrixSolver ls = new LeslieMatrixSolver(get_std_leslie(), get_std_pop(), 2000);
        ls.get_year(2010);
        System.out.println(ls);
        PowerMethodSolver ps = new PowerMethodSolver(get_std_leslie(), get_std_pop(), 10E-08);
        ps.iterate();
        //System.out.println(ps);
    }
}
