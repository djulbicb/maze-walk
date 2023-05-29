package com.example.mazewalk.old.elements;


public class Coordinate {
    private final double Y;
    private final double X;

    public Coordinate(double x, double y) {
        X = x;
        Y = y;
    }

    public double getY() {
        return Y;
    }

    public double getX() {
        return X;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "Y=" + Y +
                ", X=" + X +
                '}';
    }
}
