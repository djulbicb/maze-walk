package com.example.mazewalk.simple;

import com.example.mazewalk.Application;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Cell {

    private int rowIdx;
    private int columnIdx;
    private int label = -1;

    public Cell(int rowIdx, int columnIdx) {
        this.rowIdx = rowIdx;
        this.columnIdx = columnIdx;
    }

    private Cell east;
    private Cell south;
    private Cell west;
    private Cell north;

    private List<Cell> linked = new ArrayList<>();

    public String toString() {
        return String.format("[%s %s]", rowIdx, columnIdx);
    }

//    public boolean hasNorth() {
//        return north != null;
//    }
//    public boolean hasSouth() {
//        return south != null;
//    }
//    public boolean hasWest() {
//        return west != null;
//    }
//    public boolean hasEast() {
//        return east != null;
//    }

    public boolean isLinkToNorth() {
        return linked.contains(north);
    }
    public boolean isLinkToSouth() {
        return linked.contains(south);
    }
    public boolean isLinkToWest() {
        return linked.contains(west);
    }
    public boolean isLinkToEast() {
        return linked.contains(east);
    }


    public int getRowIdx() {
        return rowIdx;
    }

    public int getColumnIdx() {
        return columnIdx;
    }

    public Cell getEast() {
        return east;
    }

    public void setEast(Cell east) {
        this.east = east;
    }

    public Cell getSouth() {
        return south;
    }

    public void setSouth(Cell south) {
        this.south = south;
    }

    public Cell getWest() {
        return west;
    }

    public void setWest(Cell west) {
        this.west = west;
    }

    public Cell getNorth() {
        return north;
    }

    public void setNorth(Cell north) {
        this.north = north;
    }

    public List<Cell> neighbors() {
        List<Cell> neighbors = new ArrayList<>();
        if (north != null) {
            neighbors.add(north);
        }
        if (east != null) {
            neighbors.add(east);
        }
        if (west != null) {
            neighbors.add(west);
        }
        if (south != null) {
            neighbors.add(south);
        }
        return neighbors;
    }

    public AnchorPane render() {
        int size = Application.SIZE;
        int wallThickness=5;

        AnchorPane pane = new AnchorPane();
        Rectangle rectangle = new Rectangle(size, size);

        Color orange = Color.color(1, 0.6, 0.1, getOpacity(label));
        rectangle.setFill(Paint.valueOf("ccc"));
        rectangle.setFill(orange);
        rectangle.setStroke(Paint.valueOf("ddd"));


        Text text = new Text(rowIdx + " " + columnIdx);
        text.setLayoutY(20);
        text.setLayoutX(10);

        Text text1 = new Text(label + "");
        text1.setLayoutY(40);
        text1.setLayoutX(10);

        pane.getChildren().add(rectangle);
        pane.getChildren().add(text);
        pane.getChildren().add(text1);
        pane.setLayoutY(size * rowIdx);
        pane.setLayoutX(size * columnIdx);

        if (!isLinkToEast()) {
            Rectangle wall = new Rectangle(wallThickness, size);
            wall.setLayoutX(size - wallThickness);
            pane.getChildren().add(wall);
        }
        if (!isLinkToWest()) {
            pane.getChildren().add(new Rectangle(wallThickness, size));
        }
        if (!isLinkToNorth()) {
            Rectangle wall = new Rectangle(size, wallThickness);
            pane.getChildren().add(wall);
        }
        if (!isLinkToSouth()) {
            Rectangle wall = new Rectangle(size, wallThickness);
            wall.setLayoutY(size - wallThickness);
            pane.getChildren().add(wall);
        }

        pane.setOnMouseClicked(mouseEvent -> {
            System.out.println("---");
            System.out.println(String.format("Row:%s Col:%s; Links:%s", rowIdx, columnIdx, linked));
            //System.out.println(String.format("E:%s S:%s W:%s N:%s" , east.toLabel(), south.toLabel(), west.toLabel(), north.toLabel()));
        });
        return pane;
    }

    private double getOpacity(int inputValue) {
        double maxValue = Application.X * Application.Y;
        double minValue = 0.7;
        double remappedValue = (inputValue - minValue) / (maxValue - minValue);
        if (remappedValue < 0) {
            return 0;
        }
        return remappedValue;
        // return 0.5d;
    }

    public Distance distances() {
        Distance distance = new Distance(this);
        List<Cell> frontier = new ArrayList<>();
        frontier.add(this);
        distance.getDistances().put(this, 0);

        int currentDistance = 0;
        setLabel(currentDistance);
        currentDistance++;

        boolean shouldContinue = true;
        while (shouldContinue) {
            List<Cell> newFrontier = new ArrayList<>();
            for (Cell cell : frontier) {
                for (Cell linkedCell : cell.linked) {
                    if (!distance.getDistances().containsKey(linkedCell)) {
                        newFrontier.add(linkedCell);
                        distance.getDistances().put(linkedCell, currentDistance);
                        linkedCell.setLabel(currentDistance);
                    }
                }
            }
            currentDistance++;
            frontier = newFrontier;
            if (frontier.isEmpty()) {
                shouldContinue = false;
            }
        }

       return distance;
}

    private void setLabel(int currentDistance) {
        this.label = currentDistance;
    }
    public int getLabel() {
        return label;
    }

    public void link(Cell rndNeighborCell) {
        this.linked.add(rndNeighborCell);
        rndNeighborCell.linked.add(this);
    }

    public List<Cell> linked() {
        return linked;
    }
}
