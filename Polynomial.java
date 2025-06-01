import java.io.*;
import java.util.*;

public class Polynomial{
    private double[] coefficients;
    private int[] cifangs;

    public Polynomial(){
        this.coefficients = new double[] {0.0};
        this.cifangs = new int[] {0};
    }

    public Polynomial(double[] arg){
        int reallength = 0;
        for(int i = 0; i < arg.length; i++){
            if(arg[i] != 0){
                reallength++;
            }
        }
        this.coefficients = new double[reallength];
        this.cifangs = new int[reallength];
        int count = 0;
        for(int j = 0; j < arg.length; j++){
            if(arg[j] != 0){
                this.coefficients[count] = arg[j];
                this.cifangs[count] = j;
                count++;
            }
        }
    }

    public Polynomial(File file) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String s = reader.readLine();
        int length = s.length();
        reader.close();
        double[] coeffile = new double[length];
        int[] cffile = new int[length]; 
        for(int i = 0; i < length; i++){
            coeffile[i] = 0;
            cffile[i] = -1;
        }
        if(s == null|| s.isEmpty()){
            this.coefficients = new double[] {0.0};
            this.cifangs = new int[] {0};
            return;
        }
        String new_s = s.replace("-", "+-");
        String[] splitteds = new_s.split("\\+");
        for(int i = 0; i < splitteds.length; i++){
            String splitted = splitteds[i];
            if(splitted.isEmpty()){
                continue;
            }
            if(splitted.contains("x")){
                String[] parts = splitted.split("x");
                String coeff = parts[0];
                String cifang = parts[1];
                double realcoeff;
                if(coeff.equals("")){
                    realcoeff = 1.0;
                }
                else if(coeff.equals("-")){
                    realcoeff = -1.0;
                }
                else{
                    realcoeff = Double.parseDouble(coeff);
                }
                int realcifang = Integer.parseInt(cifang);
                coeffile[i] = realcoeff; 
                cffile[i] = realcifang;
            }
            else{
                double realcoeff = Double.parseDouble(splitted); 
                int realcifang = 0;
                coeffile[i] = realcoeff; 
                cffile[i] = realcifang;
            }
        }
        int nonzero = 0;
        for(int m = 0; m < length; m++){
            if(coeffile[m] != 0){
                nonzero = nonzero + 1;
            }
        }
        this.coefficients = new double[nonzero];
        this.cifangs = new int[nonzero];
        int position = 0;
        for(int n = 0; n < length; n++){
            if(coeffile[n] != 0){
                this.coefficients[position] = coeffile[n];
                this.cifangs[position] = cffile[n];
                position++;
            }
        }
    }
    
    public Polynomial(double[] arg1, int[] arg2){
        int length = arg1.length;
        this.coefficients = new double[length];
        this.cifangs = new int[length];
        System.arraycopy(arg1, 0, this.coefficients, 0, length);
        System.arraycopy(arg2, 0, this.cifangs, 0, length);
    }

    public Polynomial(int[] arg1, double[] arg2){
        int length = arg1.length;
        this.coefficients = new double[length];
        this.cifangs = new int[length];
        System.arraycopy(arg2, 0, this.coefficients, 0, length);
        System.arraycopy(arg1, 0, this.cifangs, 0, length);
    }

    public Polynomial add(Polynomial p2){
        int length = this.coefficients.length + p2.coefficients.length;
        double[] coefs = new double[length];
        int[] cfs = new int[length];
        for(int i = 0; i < length; i++){
            coefs[i] = 0;
            cfs[i] = -1;
        }
        for(int i = 0; i < this.coefficients.length; i++){
            coefs[i] = this.coefficients[i];
            cfs[i] = this.cifangs[i];
        }
        int now = this.coefficients.length;
        for(int j = 0; j < p2.coefficients.length; j++){
            int count = 0;
            for(int k = 0; k < this.coefficients.length; k++){
                if(this.cifangs[k] == p2.cifangs[j]){
                    coefs[k] = coefs[k] + p2.coefficients[j];
                    count = 1;
                }
            }
            if(count == 0){
                coefs[now] = p2.coefficients[j];
                cfs[now] = p2.cifangs[j];
                now ++;
            }
        }
        int nonzero = 0;
        for(int m = 0; m < length; m++){
            if(coefs[m] != 0){
                nonzero = nonzero + 1;
            }
        }
        double[] finalcoef = new double[nonzero];
        int[] finalcf = new int[nonzero];
        int position = 0;
        for(int n = 0; n < length; n++){
            if(coefs[n] != 0){
                finalcoef[position] = coefs[n];
                finalcf[position] = cfs[n];
                position++;
            }
        }
        return new Polynomial(finalcf, finalcoef);
    }

    public double evaluate(double x){
        double result = 0.0;
        for(int i = 0; i < this.coefficients.length; i++){
            if(this.cifangs[i] == 0){
                result = result + this.coefficients[i];
            }
            else{
                double total = 1;
                int cifang = this.cifangs[i];
                while(cifang > 0){
                    total = total * x;
                    cifang = cifang - 1;
                }
                result = result + total * this.coefficients[i];
            }
        }
        return result;
    }

    public boolean hasRoot(double x){
        if(evaluate(x)== 0.0){
            return true;
        }
        return false;
    }

    public Polynomial multiply(Polynomial p2){
        int length = this.coefficients.length * p2.coefficients.length;
        double[] coefs = new double[length];
        int[] cfs = new int[length];
        for(int i = 0; i < length; i++){
            coefs[i] = 0;
            cfs[i] = -1;
        }
        int position = 0;
        for(int i = 0; i < p2.coefficients.length; i++){
            for(int j = 0; j < this.coefficients.length; j++){
                double a = this.coefficients[j] * p2.coefficients[i];
                int b = this.cifangs[j] + p2.cifangs[i];
                int found = 0;
                for(int k = 0; k < length; k++){
                    if(cfs[k] == b){
                        found = 1;
                        coefs[k] = coefs[k] + a;
                    }
                }
                if(found == 0){
                    coefs[position] = a;
                    cfs[position] = b;
                    position++;
                }
            }
        }
        int nonzero = 0;
        for(int m = 0; m < length; m++){
            if(coefs[m] != 0){
                nonzero = nonzero + 1;
            }
        }
        double[] finalcoef = new double[nonzero];
        int[] finalcf = new int[nonzero];
        int position2 = 0;
        for(int n = 0; n < length; n++){
            if(coefs[n] != 0){
                finalcoef[position2] = coefs[n];
                finalcf[position2] = cfs[n];
                position2++;
            }
        }
        return new Polynomial(finalcf, finalcoef);
    }

    public void saveToFile(String stroutput) throws IOException{
        StringBuilder built = new StringBuilder();
        for (int i = 0; i < this.coefficients.length; i++) {
            double c = this.coefficients[i];
            int e = this.cifangs[i];
            if(i == 0){
                if(e == 0){
                    built.append(c); 
                }
                else{
                    built.append(c).append("x").append(e);
                }
            }
            else{
                if(c > 0){
                    built.append("+");
                }
                if(e == 0){
                    built.append(c);
                }
                else{
                    built.append(c).append("x").append(e);
                }
            }
        }
        String outputLine = built.toString();
        BufferedWriter writer = new BufferedWriter(new FileWriter(stroutput));
        writer.write(outputLine);
        writer.newLine();
        writer.close();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < coefficients.length; i++) {
            sb.append(coefficients[i]).append("x").append(cifangs[i]);
            if (i < coefficients.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }


}