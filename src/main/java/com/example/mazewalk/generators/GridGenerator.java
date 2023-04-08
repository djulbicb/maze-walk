package com.example.mazewalk.generators;

import com.example.mazewalk.elements.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class GridGenerator {
    public List<Coordinate> generateGrid(int X, int Y) {
        List<Coordinate> all = new ArrayList<>(X * Y);
        for (int gridRowIdx = 0; gridRowIdx < Y; gridRowIdx++) {
            for (int gridColIdx = 0; gridColIdx < X; gridColIdx++) {
                all.add(new Coordinate(gridColIdx, gridRowIdx));
            }
        }
        return all;
    }

    public List<Coordinate> generateFromImage(String fileUrl, int X, int Y) {
        List<Coordinate> all = new ArrayList<>(X * Y);

        return all;
    }
}
