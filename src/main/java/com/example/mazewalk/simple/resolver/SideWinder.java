package com.example.mazewalk.simple.resolver;

import com.example.mazewalk.simple.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SideWinder {
    public void resolve(Cell[][] grid) {
        Random random = new Random();

        for (Cell[] row : grid) {
            List<Cell> continousChain = new ArrayList<>();
            for (Cell col : row) {
                continousChain.add(col);

                boolean atEastBorder = col.getEast() == null;
                boolean notAtNorthBorder = col.getNorth() != null;
                boolean randomLuck = random.nextInt(2) == 0;

                boolean willLinkGridRows = atEastBorder || (notAtNorthBorder && randomLuck);
                if(willLinkGridRows) {
                    Cell rndChainCell = continousChain.get(random.nextInt(continousChain.size()));

                    if (rndChainCell.getNorth() != null) {
                        rndChainCell.link(rndChainCell.getNorth());
                    }
                    continousChain.clear();
                } else {
                    col.link(col.getEast());
                }
            }
        }

    }
}
