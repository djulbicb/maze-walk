package com.example.mazewalk.old.elements.shapes;

import com.example.mazewalk.Application;
import com.example.mazewalk.old.elements.Coordinate;
import com.example.mazewalk.old.elements.Port;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.HashSet;
import java.util.Set;


public class Hex {
    int WALL_THICKNESS = 3;
    private Paint color = new Color(0.5,0.5,0.5,0.5);

    public Node render() {
        Rectangle r = new Rectangle(SIZE, SIZE);
       // r.setFill(Paint.valueOf("6cc"));
        r.setFill(color);
        root.getChildren().add(r);

        double zero = 0;
        double size = SIZE;
        double half = SIZE / 2;
        double third = SIZE / 3.33;

        double hexagonalSide = (2/3.0) * SIZE;
        double middle = SIZE / 2.0;
        double magic = Math.sqrt(3.0) / 2;

        Coordinate v1 = new Coordinate(middle - hexagonalSide, middle);
        Coordinate v2 = new Coordinate(middle - hexagonalSide/2, middle + magic * hexagonalSide);
        Coordinate v3 = new Coordinate(middle + hexagonalSide/2, middle + magic * hexagonalSide);
        Coordinate v4 = new Coordinate(middle + hexagonalSide, middle);
        Coordinate v5 = new Coordinate(middle + hexagonalSide/2, middle - magic * hexagonalSide);
        Coordinate v6 = new Coordinate(middle - hexagonalSide/2, middle - magic * hexagonalSide);


        Polyline polyline = new Polyline();
        polyline.getPoints().addAll(
                    v1.getX(), v1.getY(),
                    v2.getX(), v2.getY(),
                    v3.getX(), v3.getY(),
                    v4.getX(), v4.getY(),
                    v5.getX(), v5.getY(),
                    v6.getX(), v6.getY(),
                    v1.getX(), v1.getY()
                );
        root.getChildren().add(polyline);

        Text text = new Text(getKey());
        root.getChildren().add(text);

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
        return String.format("%s_%s", (int)coordinate.getX() , (int)coordinate.getY());
    }

    public Hex(Coordinate coordinate) {


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
    public void openPort(Hex nextHex) {
        Coordinate next = nextHex.getCoordinate();
        if (coordinate.getY() == next.getY()) {
            if (next.getX() > coordinate.getX()) {
                ports.add(Port.RIGHT);
                nextHex.openPort(Port.LEFT);
            } else if (next.getX() < coordinate.getX()) {
                ports.add(Port.LEFT);
                nextHex.openPort(Port.RIGHT);
            }
        }

        if (coordinate.getX() == next.getX()) {
            if (next.getY() > coordinate.getY()) {
                ports.add(Port.DOWN);
                nextHex.openPort(Port.UP);
            } else if (next.getY() < coordinate.getY()) {
                ports.add(Port.UP);
                nextHex.openPort(Port.DOWN);
            }
        }

    }
}
