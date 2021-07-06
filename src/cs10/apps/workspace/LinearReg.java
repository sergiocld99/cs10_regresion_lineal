package cs10.apps.workspace;

import cs10.apps.model.Point;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;

public class LinearReg {
    private final ArrayList<Point> points;

    public LinearReg() {
        this.points = new ArrayList<>();
    }

    public void addPoint(double x, double y){
        points.add(new Point(x,y));
    }

    public void addPoints(double @NotNull[] x, double @NotNull[] y){
        if (x.length < y.length) System.err.println("Missing values in x");
        else if (x.length > y.length) System.err.println("Missing values in y");
        else for (int i=0; i<x.length; i++) addPoint(x[i], y[i]);
    }

    public double sumXi(){
        double sum = 0;
        for (Point point : points) sum += point.getX();
        return sum;
    }

    public double sumYi(){
        double sum = 0;
        for (Point point : points) sum += point.getY();
        return sum;
    }

    public double sumXiYi(){
        double sum = 0;
        for (Point point : points) sum += (point.getX() * point.getY());
        return sum;
    }

    public double sumXi2(){
        double sum = 0;
        for (Point point : points) sum += Math.pow(point.getX(), 2);
        return sum;
    }

    public double sumYi2(){
        double sum = 0;
        for (Point point : points) sum += Math.pow(point.getY(), 2);
        return sum;
    }

    public int n(){
        return points.size();
    }

    public double averageX(){
        return sumXi() / n();
    }

    public double averageY(){
        return sumYi() / n();
    }

    public double Sxx(){
        return sumXi2() - n() * Math.pow(averageX(), 2);
    }

    public double Syy(){
        return sumYi2() - n() * Math.pow(averageY(), 2);
    }

    public double Sxy(){
        return sumXiYi() - n() * averageX() * averageY();
    }

    public double beta1(){
        return Sxy() / Sxx();
    }

    public double beta0(){
        return averageY() - beta1() * averageX();
    }

    public String linearY(){
        double m = beta1();
        char sign = m < 0 ? '-' : '+';
        return beta0() + " " + sign + " " + Math.abs(m) + " " + "x";
    }

    public double approximate(double x){
        return beta0() + beta1() * x;
    }

    public double SSr(){
        return Syy() - Math.pow(Sxy(), 2) / Sxx();
    }

    public double R2(){
        return 1 - SSr() / Syy();
    }

    public double variation(){
        return (SSr() / (n() - 2));
    }

    public double deviation(){
        return Math.sqrt(variation());
    }

    public void clear(){
        points.clear();
    }

    public void savePoints(File file){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Point point : points){
                bw.write(String.valueOf(point.getX())); bw.write(' ');
                bw.write(String.valueOf(point.getY())); bw.write('\n');
            }
        } catch (Exception e){
            System.err.println("Unable to save file: " + e.getMessage());
        }
    }

    public void restorePoints(File file){
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null){
                String[] par = line.split(" ");
                addPoint(Double.parseDouble(par[0]), Double.parseDouble(par[1]));
            }

            printPoints();

        } catch (Exception e){
            System.err.println("Unable to restore values: " + e.getMessage());
        }
    }

    public void printPoints(){
        for (Point point : points) System.out.print("(" + point.getX() + ", " + point.getY() + ") ");
        System.out.println();
    }

    public void showSums() {
        System.out.println("Sum Xi: " + sumXi());
        System.out.println("Sum Yi: " + sumYi());
        System.out.println("Sum Xi^2: " + sumXi2());
        System.out.println("Sum Yi^2: " + sumYi2());
        System.out.println("Sum Xi*Yi: " + sumXiYi());
        System.out.println("Sxx: " + Sxx());
        System.out.println("Sxy: " + Sxy());
        System.out.println("Syy: " + Syy());
    }

    public void showAverages() {
        System.out.println("Average x: " + averageX());
        System.out.println("Average y: " + averageY());
    }
}
