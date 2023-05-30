package com.example.mazewalk;


import com.example.mazewalk.simple.Cell;
import com.example.mazewalk.simple.DijkstraPathSolver;
import com.example.mazewalk.simple.Distance;
import com.example.mazewalk.simple.Grid;
import com.example.mazewalk.simple.resolver.SideWinder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application extends javafx.application.Application {
    public static int SIZE = 50;
    public static int HALF_SIZE = SIZE / 2;
    static int X = 5;
    static int Y = 5;

    AnchorPane pane = new AnchorPane();

    //    @Override
    public void start(Stage stage) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        Scene scene = new Scene(pane, SIZE * X, SIZE * Y);

        Grid grid = new Grid(X,Y);
        // BinaryTree tree = new BinaryTree();
        // tree.resolve(grid);

        SideWinder winder = new SideWinder();
        DijkstraPathSolver solver = new DijkstraPathSolver();

        winder.resolve(grid.getGrid());

        grid.savePng();

        Cell start = grid.getRandomCell();
        System.out.println("Picked: " + start);
        Distance distances = start.distances();

        Cell end = grid.getRandomCell();

        List<Cell> cells = solver.pathTo(distances, start, end);



        // System.out.println(grid.toString());
        System.out.println(grid.toAscii());

        pane.getChildren().addAll(grid.render());
        draw(cells);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    private void draw(List<Cell> cells) {
        Polyline polyline = new Polyline();
        for (Cell cell : cells) {
            int columnIdx = cell.getColumnIdx();
            int rowIdx = cell.getRowIdx();


            polyline.setStroke(Color.RED);
            polyline.setStrokeWidth(5.0);
            polyline.getPoints().addAll(
                    columnIdx * SIZE * 1.0 + HALF_SIZE, rowIdx * SIZE * 1.0 + HALF_SIZE
            );
        }

        pane.getChildren().add(polyline);
    }


    public static void main(String[] args) {
        launch();
    }
}