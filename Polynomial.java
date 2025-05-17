public class Polynomial {
    private double[] coeffs;

    public Polynomial(){
        this.coeffs = new double[]{0.0};
    }
    public Polynomial(double[] coeffs) {
        int lastNonZero = coeffs.length - 1;
        while (lastNonZero > 0 && coeffs[lastNonZero] == 0.0) {
            lastNonZero--;
        }
        this.coeffs = new double[lastNonZero + 1];
        System.arraycopy(coeffs, 0, this.coeffs, 0, lastNonZero + 1);
    }
    public Polynomial add(Polynomial other) {
        int maxLen = Math.max(this.coeffs.length, other.coeffs.length);
        double[] sumCoeffs = new double[maxLen];
        for (int i = 0; i < maxLen; i++) {
            double a = i < this.coeffs.length ? this.coeffs[i] : 0.0;
            double b = i < other.coeffs.length ? other.coeffs[i] : 0.0;
            sumCoeffs[i] = a + b;
        }
        return new Polynomial(sumCoeffs);
    }
    public double evaluate(double x) {
        double result = 0.0;
        double power = 1.0;
        for (double c : this.coeffs) {
            result += c * power;
            power *= x;
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0.0;
    }
}