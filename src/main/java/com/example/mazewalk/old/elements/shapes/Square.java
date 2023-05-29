package com.example.mazewalk.old.elements.shapes;

import com.example.mazewalk.Application;
import com.example.mazewalk.old.elements.Coordinate;
import com.example.mazewalk.old.elements.Port;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;


public class Square {
    int WALL_THICKNESS = 3;
    private Paint color = new Color(0.5,0.5,0.5,0.5);

    public Node render() {
        Rectangle r = new Rectangle(SIZE, SIZE);
       // r.setFill(Paint.valueOf("6cc"));
        r.setFill(color);
        root.getChildren().add(r);

        if (!ports.contains(Port.UP)) {
            Rectangle rectangle = new Rectangle(SIZE, WALL_THICKNESS);
            rectangle.fillProperty().set(Paint.valueOf("444"));
            root.getChildren().add(rectangle);
        }
        if (!ports.contains(Port.DOWN)) {
            Rectangle rectangle = new Rectangle(SIZE, WALL_THICKNESS);
            rectangle.setTranslateY(SIZE - WALL_THICKNESS);
            rectangle.fillProperty().set(Paint.valueOf("444"));
            root.getChildren().add(rectangle);
        }
        if (!ports.contains(Port.LEFT)) {
            Rectangle rectangle = new Rectangle(WALL_THICKNESS, SIZE);
            rectangle.fillProperty().set(Paint.valueOf("444"));
            root.getChildren().add(rectangle);
        }
        if (!ports.contains(Port.RIGHT)) {
            Rectangle rectangle = new Rectangle(WALL_THICKNESS, SIZE);
            rectangle.fillProperty().set(Paint.valueOf("444"));
            rectangle.setTranslateX(SIZE - WALL_THICKNESS);
            root.getChildren().add(rectangle);
        }

        root.setBackground(Background.EMPTY);
        //root.setStyle("-fx-background-color: transparent; -fx-border-color: ddd; -fx-border-width: 1px 1px 1px 1px");
        return root;
    }



    private final Coordinate coordinate;
    private final AnchorPane root;
    int SIZE = Application.SIZE;

//    String border = "000";
//    String content = "ccc";

    Set<Port> ports = new HashSet<>();

    int contacts = 0;

    public String getKey() {
        return String.format("%s_%s", coordinate.getX(), coordinate.getY());
    }

    public Square(Coordinate coordinate) {


//        Rectangle rect = new Rectangle(SIZE, SIZE, SIZE, SIZE);
////        rect.fillProperty().set(Paint.valueOf("ccc"));
////        rect.strokeProperty().set(Paint.valueOf("000"));
        this.coordinate = coordinate;

        root = new AnchorPane();
        root.setTranslateX(SIZE * coordinate.getX());
        root.setTranslateY(SIZE * coordinate.getY());
        root.setMinHeight(SIZE);
        root.setMaxHeight(SIZE);
        root.setOnMouseClicked(mouseEvent -> {
            System.out.println(coordinate);
            System.out.println(ports);
            System.out.println("_________");
        });
    }

    public Pair<Double, Double> getMiddle() {
        double x = root.getTranslateX() + SIZE / 2;
        double y = root.getTranslateY() + SIZE / 2;
        return new Pair(x, y);
    }
    public void setColor(String ddd) {
        this.color = Paint.valueOf(ddd);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void openPort(Port port) {
        ports.add(port);
    }
    public void openPort(Square nextBlock) {
        Coordinate next = nextBlock.getCoordinate();
        if (coordinate.getY() == next.getY()) {
            if (next.getX() > coordinate.getX()) {
                ports.add(Port.RIGHT);
                nextBlock.openPort(Port.LEFT);
            } else if (next.getX() < coordinate.getX()) {
                ports.add(Port.LEFT);
                nextBlock.openPort(Port.RIGHT);
            }
        }

        if (coordinate.getX() == next.getX()) {
            if (next.getY() > coordinate.getY()) {
                ports.add(Port.DOWN);
                nextBlock.openPort(Port.UP);
            } else if (next.getY() < coordinate.getY()) {
                ports.add(Port.UP);
                nextBlock.openPort(Port.DOWN);
            }
        }

    }
}
