package com.example.mazewalk.simple.pathResolver;

import com.example.mazewalk.simple.Cell;
import com.example.mazewalk.simple.Distance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DijkstraPathSolver {
    public List<Cell> pathTo(Distance distances, Cell start, Cell end) {
        if (start == end) {
            return Collections.emptyList();
        }
        System.out.println("start " + start);
        System.out.println("end " + end);

        Cell current = end;
        boolean keepSearching = true;
        List<Cell> breadcrumbs = new ArrayList<>();
        breadcrumbs.add(end);

        while (keepSearching) {
            List<Cell> linked = current.linked();
            for (Cell neighbour : linked) {
                if (distances.getDistances().get(current) > distances.getDistances().get(neighbour)) {
                    breadcrumbs.add(neighbour);
                    current = neighbour;

                    if (current == start) {
                        keepSearching = false;
                    }

                    break;
                }
            }
        }
        return breadcrumbs;
    }
}
