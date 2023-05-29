package com.example.mazewalk.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BinaryTree {
    public void resolve(Cell[][] grid) {
        Random random = new Random();

        for (Cell[] row : grid) {
            for (Cell col : row) {
                List<Cell> neighbors = new ArrayList<>();
                if (col.getNorth() != null) {
                    neighbors.add(col.getNorth());
                }
                if (col.getEast() != null) {
                    neighbors.add(col.getEast());
                }

                if (neighbors.isEmpty()) {
                    continue;
                }

                Cell rndNeighborCell = neighbors.get(random.nextInt(neighbors.size()));
                col.link(rndNeighborCell);
            }
        }

    }
}
