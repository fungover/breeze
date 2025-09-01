package org.fungover.breeze.geometry;

public class Triangle {
    private double a;
    private double b;
    private double c;

    // Konstruktor
    public Triangle(double a, double b, double c) {
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new IllegalArgumentException("Sidornas längd måste vara större än 0.");
        }
        if (!isValidTriangle(a, b, c)) {
            throw new IllegalArgumentException("Ogiltig triangel enligt triangelolikheten.");
        }
        this.a = a;
        this.b = b;
        this.c = c;
    }

    private boolean isValidTriangle(double a, double b, double c) {
        return a + b > c && a + c > b && b + c > a;
    }

    // Beräkna triangelns omkrets
    public double getPerimeter() {
        return a + b + c;
    }

    // Beräkna triangelns area med Herons formel
    public double getArea() {
        double s = getPerimeter() / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    // Getters
    public double getA() { return a; }
    public double getB() { return b; }
    public double getC() { return c; }
}
