package com.example.mazewalk.simple;

import com.example.mazewalk.Application;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Cell {

    private int rowIdx;
    private int columnIdx;

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

    public boolean goesNorth() {
        return linked.contains(north);
    }
    public boolean goesSouth() {
        return linked.contains(south);
    }
    public boolean goesWest() {
        return linked.contains(west);
    }
    public boolean goesEast() {
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
        rectangle.setFill(Paint.valueOf("ccc"));
        rectangle.setStroke(Paint.valueOf("ddd"));
        Text text = new Text(rowIdx + " " + columnIdx);
        text.setLayoutY(10);
        pane.getChildren().add(rectangle);
        pane.getChildren().add(text);
        pane.setLayoutY(size * rowIdx);
        pane.setLayoutX(size * columnIdx);

        if (!goesEast()) {
            Rectangle wall = new Rectangle(wallThickness, size);
            wall.setLayoutX(size - wallThickness);
            pane.getChildren().add(wall);
        }
        if (!goesWest()) {
            pane.getChildren().add(new Rectangle(wallThickness, size));
        }
        if (!goesNorth()) {
            Rectangle wall = new Rectangle(size, wallThickness);
            pane.getChildren().add(wall);
        }
        if (!goesSouth()) {
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

    public void link(Cell rndNeighborCell) {
        this.linked.add(rndNeighborCell);
        rndNeighborCell.linked.add(this);
    }
}
