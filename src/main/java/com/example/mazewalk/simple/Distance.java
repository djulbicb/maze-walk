package com.example.mazewalk.simple;

import java.util.HashMap;

public class Distance {

    private Cell root;
    private HashMap<Cell, Integer> distances;

    public Distance(Cell root) {
        this.root = root;
        this.distances = new HashMap<>();
    }

    public HashMap<Cell, Integer> getDistances() {
        return distances;
    }
}
