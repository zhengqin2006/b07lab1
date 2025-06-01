package lab2;
import java.io.*;

public class Driver{
    public static void main(String[] args){
        try{
            double[] arr0 = {7, 1, 2, 3};
            Polynomial p0 = new Polynomial(arr0);
            System.out.println("p0 = " + p0);
            System.out.println("p0.evaluate(3) = " + p0.evaluate(3));
            System.out.println("p0.hasRoot(0) ? " + p0.hasRoot(0));
            double[] arr1 = { 6.0,  0.0,  0.0,  5.0 };
            double[] arr2 = { 0.0, -2.0,  0.0,  0.0, -9.0 };
            Polynomial p1 = new Polynomial(arr1);
            Polynomial p2 = new Polynomial(arr2);
            System.out.println("p1 = " + p1); 
            System.out.println("p2 = " + p2);  
            Polynomial sum = p1.add(p2);
            System.out.println("p1 + p2 = " + sum);
            double x = 0.1;
            System.out.println("sum.evaluate(0.1) = " + sum.evaluate(x));
            System.out.println("sum.hasRoot(1)? " + sum.hasRoot(1));
            Polynomial product = p1.multiply(p2);
            System.out.println("p1 * p2 = " + product);
            System.out.println("p1 * p2 = " + product.evaluate(2));
            File infile = new File("poly_input.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(infile));
            bw.write("5-3x2+7x8"); 
            bw.newLine();
            bw.close();
            Polynomial fromFile = new Polynomial(infile);
            Polynomial modified = fromFile.multiply(new Polynomial(new double[]{1.0, 1.0})); 
            System.out.println("modified = " + modified);
            String outfileName = "poly_output.txt";
            modified.saveToFile(outfileName);
        } 
        catch(IOException e){
            System.err.println("!!!!!" + e.getMessage());
        }
    }
}