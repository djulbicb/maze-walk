package com.example.mazewalk;


import com.example.mazewalk.elements.Block;
import com.example.mazewalk.elements.Coordinate;
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

    HashMap<String, Block> allRectangles = new HashMap<>();
    HashMap<String, Block> remainingRectangles = new HashMap<>();
    HashMap<String, Block> usedRectangles = new HashMap<>();
    List<Block> path = new ArrayList<>();

//    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        AnchorPane pane = new AnchorPane();

        Scene scene = new Scene(pane, SIZE * X, SIZE * Y);
        scene.setFill(Paint.valueOf("888"));
        for (int gridRowIdx = 0; gridRowIdx < Y; gridRowIdx++) {
            for (int gridColIdx = 0; gridColIdx < X; gridColIdx++) {
                Block rect = new Block(new Coordinate(gridColIdx, gridRowIdx));
                allRectangles.put(rect.getKey(), rect);
            }
        }
        remainingRectangles.putAll(allRectangles);

        Block currentBlock = allRectangles.get("0_0");
        usedRectangles.put(currentBlock.getKey(), currentBlock);
        boolean condition = true;

        Polyline polyline = new Polyline();
        addToPolyline(polyline, currentBlock);
        List<Polyline> polylines = new ArrayList<>();
        polylines.add(polyline);

        Color nextColor = nextColor();
        while (condition) {
            path.add(currentBlock);
            HashMap<String, Block> availableRect = getAvailableRect(currentBlock, allRectangles);
            if (availableRect.size() == 0) {

                Optional<Block> Block1 = pickSomeOtherOne(path);
                if (Block1.isEmpty()) {
                    condition = false;
                    break;
                } else {
                    Block nextBlock = Block1.get();
                    currentBlock.openPort(nextBlock);
                    currentBlock = nextBlock;

                    polyline = new Polyline();
                    polylines.add(polyline);

                    availableRect = getAvailableRect(Block1.get(), allRectangles);
                    nextColor = nextColor();
                    System.out.println("FOunt");


                    addToPolyline(polyline, currentBlock);

                }
            }

            Block nextBlock = getRandom(availableRect);
            currentBlock.openPort(nextBlock);
            currentBlock = nextBlock;

            currentBlock.setColor(nextColor.toString());
            path.add(currentBlock);
            addToPolyline(polyline, currentBlock);
            usedRectangles.put(currentBlock.getKey(), currentBlock);

            remainingRectangles.remove(currentBlock.getKey());
        }

        boolean skip = true;
        for (Block value : allRectangles.values()) {
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

    private void addToPolyline(Polyline polyline, Block Block) {
        polyline.getPoints().addAll(Block.getMiddle().getKey(), Block.getMiddle().getValue());
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

    private Optional<Block> pickSomeOtherOne(List<Block> path) {
        for (Block Block : path) {
            HashMap<String, Block> availableRect = getAvailableRect(Block, remainingRectangles);
            if (availableRect.size() > 0) {
                return Optional.of(Block);
            }
        }
        return Optional.empty();
    }

    private Block getRandom(HashMap<String, Block> availableRect) {
        Random generator = new Random();
        Object[] objects = availableRect.values().toArray();
        int i = generator.nextInt(objects.length);
        System.out.println(i);
        return (Block) objects[i];
    }


    private HashMap<String, Block> getAvailableRect(Block Block, HashMap<String, Block> allRectangles) {
        List<String> possible = new ArrayList<>();
        int x = Block.getCoordinate().getX();
        int y = Block.getCoordinate().getY();

        possible.add(getKey(x + 0, y + -1));
        possible.add(getKey(x + 0, y + 1));
        possible.add(getKey(x + -1, y + 0));
        possible.add(getKey(x + 1, y + 0));


        possible.remove(Block.getKey());
        possible.removeAll(usedRectangles.keySet());

        HashMap<String, Block> rects = new HashMap<>();
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