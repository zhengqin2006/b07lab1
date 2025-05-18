public class Polynomial{
    private double[] coefficients;

    public Polynomial(){
        this.coefficients = new double[]{0.0};
    }

    public Polynomial(double[] arg){
        int length = arg.length;
        this.coefficients = new double[length];
        System.arraycopy(arg, 0, this.coefficients, 0, length);
    }

    public Polynomial add(Polynomial p2){
        int longer;
        if(this.coefficients.length >= p2.coefficients.length){
            longer = this.coefficients.length;
        }
        else{
            longer = p2.coefficients.length;
        }
        double[] total = new double[longer];
        for(int i = 0; i < longer; i++){
            double a = 0;
            double b = 0;
            if(i < this.coefficients.length){
                a = this.coefficients[i];
            }
            if(i < p2.coefficients.length){
                b = p2.coefficients[i];
            }
            total[i] = a + b;
        }
        return new Polynomial(total);
    }

    public double evaluate(double x){
        double result = 0.0;
        double now_x = 1.0;
        for(double coefficient : this.coefficients){
            result += coefficient *now_x;
            now_x = now_x *x;
        }
        return result;
    }

    public boolean hasRoot(double x){
        if(evaluate(x)== 0.0){
            return true;
        }
        return false;
    }
}