package com.example.mazewalk.old;


import com.example.mazewalk.old.elements.Coordinate;
import com.example.mazewalk.old.elements.shapes.Hex;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class HelloApplication extends Application {
    public static int SIZE = 30;
    static int X = 20;
    static int Y = 10;

    HashMap<String, Hex> allRectangles = new HashMap<>();
    HashMap<String, Hex> remainingRectangles = new HashMap<>();
    HashMap<String, Hex> usedRectangles = new HashMap<>();
    List<Hex> path = new ArrayList<>();

//    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        AnchorPane pane = new AnchorPane();

        Scene scene = new Scene(pane, SIZE * X, SIZE * Y);
        scene.setFill(Paint.valueOf("888"));
        for (int gridRowIdx = 0; gridRowIdx < Y; gridRowIdx++) {
            for (int gridColIdx = 0; gridColIdx < X; gridColIdx++) {
                Hex rect = new Hex(new Coordinate(gridColIdx, gridRowIdx));
                allRectangles.put(rect.getKey(), rect);
            }
        }
        remainingRectangles.putAll(allRectangles);

        Hex currentHex = allRectangles.get("0_0");
        usedRectangles.put(currentHex.getKey(), currentHex);
        boolean condition = true;

        Polyline polyline = new Polyline();
        addToPolyline(polyline, currentHex);
        List<Polyline> polylines = new ArrayList<>();
        polylines.add(polyline);

        Color nextColor = nextColor();
        while (condition) {
            path.add(currentHex);
            HashMap<String, Hex> availableRect = getAvailableRect(currentHex, allRectangles);
            if (availableRect.size() == 0) {

                Optional<Hex> Block1 = pickSomeOtherOne(path);
                if (Block1.isEmpty()) {
                    condition = false;
                    break;
                } else {
                    Hex nextHex = Block1.get();
                    currentHex.openPort(nextHex);
                    currentHex = nextHex;

                    polyline = new Polyline();
                    polylines.add(polyline);

                    availableRect = getAvailableRect(Block1.get(), allRectangles);
                    nextColor = nextColor();
                    System.out.println("FOunt");


                    addToPolyline(polyline, currentHex);

                }
            }

            Hex nextHex = getRandom(availableRect);
            currentHex.openPort(nextHex);
            currentHex = nextHex;

            currentHex.setColor(nextColor.toString());
            path.add(currentHex);
            addToPolyline(polyline, currentHex);
            usedRectangles.put(currentHex.getKey(), currentHex);

            remainingRectangles.remove(currentHex.getKey());
        }

        boolean skip = true;
        for (Hex value : allRectangles.values()) {
//            if (skip) {
//                skip = false;
//                continue;
//            }
            pane.getChildren().add(value.render());
        }

//        pane.getChildren().addAll(polylines);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    private void addToPolyline(Polyline polyline, Hex Hex) {
        polyline.getPoints().addAll(Hex.getMiddle().getKey(), Hex.getMiddle().getValue());
    }

    Random r = new Random();
    private Color nextColor() {
        // return Color.TRANSPARENT;
        //return Color.color(Math.random(), Math.random(), Math.random());
        return Color.color(ligthColor(), ligthColor(), ligthColor(), 0.75);
    }

    private double ligthColor() {
        double rangeMin = 0.5;
        double rangeMax = 0.9;
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }

    private Optional<Hex> pickSomeOtherOne(List<Hex> path) {
        for (Hex Hex : path) {
            HashMap<String, Hex> availableRect = getAvailableRect(Hex, remainingRectangles);
            if (availableRect.size() > 0) {
                return Optional.of(Hex);
            }
        }
        return Optional.empty();
    }

    private Hex getRandom(HashMap<String, Hex> availableRect) {
        Random generator = new Random();
        Object[] objects = availableRect.values().toArray();
        int i = generator.nextInt(objects.length);
        System.out.println(i);
        return (Hex) objects[i];
    }


    private HashMap<String, Hex> getAvailableRect(Hex Hex, HashMap<String, Hex> allRectangles) {
        List<String> possible = new ArrayList<>();
        int x = (int) Hex.getCoordinate().getX();
        int y = (int) Hex.getCoordinate().getY();

        possible.add(getKey(x + 0, y + -1));
        possible.add(getKey(x + 0, y + 1));
        possible.add(getKey(x + -1, y + 0));
        possible.add(getKey(x + 1, y + 0));


        possible.remove(Hex.getKey());
        possible.removeAll(usedRectangles.keySet());

        HashMap<String, Hex> rects = new HashMap<>();
        for (String s : possible) {
            if (allRectangles.containsKey(s)) {
                rects.put(s, allRectangles.get(s));
            }
        }
        return rects;
    }

    public String getKey(int X, int Y) {
        return String.format("%s_%s", X, Y);
    }


    public static void main(String[] args) {
        launch();
    }
}