package com.example.mazewalk;


import com.example.mazewalk.simple.BinaryTree;
import com.example.mazewalk.simple.Grid;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    public static int SIZE = 50;
    static int X = 5;
    static int Y = 5;

//    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        AnchorPane pane = new AnchorPane();
        Scene scene = new Scene(pane, SIZE * X, SIZE * Y);

        Grid grid = new Grid(X,Y);
        BinaryTree tree = new BinaryTree();
        tree.resolve(grid.getGrid());

        System.out.println(grid.toString());
        System.out.println(grid.toAscii());

        pane.getChildren().addAll(grid.render());

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}