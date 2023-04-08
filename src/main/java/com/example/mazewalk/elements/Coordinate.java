package com.example.mazewalk.elements;


public class Coordinate {
    private final int Y;
    private final int X;

    public Coordinate(int x, int y) {
        X = x;
        Y = y;
    }

    public int getY() {
        return Y;
    }

    public int getX() {
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
