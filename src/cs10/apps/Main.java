package cs10.apps;

import cs10.apps.workspace.LinearReg;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // variables
        double[] x = null, y = null;
        LinearReg linearReg = new LinearReg();

        // Load cached values
        File file = new File("values.txt");
        if (file.exists()) linearReg.restorePoints(file);

        // Prepare to read
        Scanner scanner = new Scanner(System.in);
        if (linearReg.n() == 0) System.out.println("Welcome! Type arrays as x=n1 n2 n3, then type calc " +
                "Commands: approx N, averages, calc, clear, d, n, r2, ssr, sums, sxx, sxy, syy, v");
        String read = scanner.nextLine();

        while (read != null && !read.equals("close")){
            if (read.startsWith("x=")) x = loadVector(read.replace("x=", ""));
            else if (read.startsWith("y=")) y = loadVector(read.replace("y=",""));
            else if (read.startsWith("approx")) doApprox(linearReg, read.replace("approx", ""));
            else if (read.equals("sums")) linearReg.showSums();
            else if (read.equals("n")) System.out.println(linearReg.n());
            else if (read.equals("v")) System.out.println(linearReg.variation());
            else if (read.equals("d")) System.out.println(linearReg.deviation());
            else if (read.equals("ssr")) System.out.println(linearReg.SSr());
            else if (read.equals("sxx")) System.out.println(linearReg.Sxx());
            else if (read.equals("sxy")) System.out.println(linearReg.Sxy());
            else if (read.equals("syy")) System.out.println(linearReg.Syy());
            else if (read.equals("r2")) System.out.println(linearReg.R2());
            else if (read.equals("averages")) linearReg.showAverages();
            else if (read.equals("calc")){
                if (x != null && y != null) linearReg.addPoints(x,y);
                if (linearReg.n() > 0) {
                    System.out.println(linearReg.linearY());
                    linearReg.savePoints(file);
                } else System.err.println("No data loaded yet");
            } else if (read.equals("clear")) linearReg.clear();

            // read next
            read = scanner.nextLine();
        }

        scanner.close();
    }

    private static void doApprox(LinearReg linearReg, String approx) {
        try {
            double value = Double.parseDouble(approx.trim());
            double result = linearReg.approximate(value);
            System.out.println("Estimated y("+value+") = " + result);
        } catch (Exception e){
            System.err.println("Incorrect format: " + approx);
        }
    }

    public static double[] loadVector(String read){
        read = read.replace("[","").replace("]","");
        String[] values = read.split(" ");
        double[] v = new double[values.length];
        try {
            for (int i=0; i<values.length; i++) v[i] = Double.parseDouble(values[i]);
        } catch (Exception e){
            System.err.println("Incorrect format: " + read);
        }
        return v;
    }
}
