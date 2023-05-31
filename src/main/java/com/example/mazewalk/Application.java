package com.example.mazewalk;


import com.example.mazewalk.simple.Cell;
import com.example.mazewalk.simple.pathResolver.DijkstraPathSolver;
import com.example.mazewalk.simple.Distance;
import com.example.mazewalk.simple.Grid;
import com.example.mazewalk.simple.resolver.SideWinder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Application extends javafx.application.Application {
    public static int SIZE = 50;
    public static int HALF_SIZE = SIZE / 2;
    public static int X = 15;
    public static int Y = 15;

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

        Cell start = grid.getCell(0,0);// grid.getRandomCell();
        System.out.println("Picked: " + start);
        Distance distances = start.distances();

        Cell end = grid.getCell(X - 1,Y - 1);// grid.getRandomCell();

        List<Cell> cells = solver.pathTo(distances, start, end);



        // System.out.println(grid.toString());
        System.out.println(grid.toAscii());

        pane.getChildren().addAll(grid.render());
        draw(cells);

        savePaneAsImage(pane, "test.png");

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    private static void savePaneAsImage(Pane pane, String filename) {
        WritableImage image = pane.snapshot(new SnapshotParameters(), null);

        File file = new File(filename);
        try {
            ImageIO.write(convertToBufferedImage(image), "png", file);
            System.out.println("Image saved as " + filename);
        } catch (IOException e) {
            System.out.println("Failed to save image: " + e.getMessage());
        }
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

    private static BufferedImage convertToBufferedImage(WritableImage writableImage) {
        int width = (int) writableImage.getWidth();
        int height = (int) writableImage.getHeight();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Get the pixel data from the WritableImage
        int[] buffer = new int[width * height];
        writableImage.getPixelReader().getPixels(0, 0, width, height, javafx.scene.image.PixelFormat.getIntArgbInstance(), buffer, 0, width);

        // Set the pixel data to the BufferedImage
        bufferedImage.setRGB(0, 0, width, height, buffer, 0, width);

        return bufferedImage;
    }

    public static void main(String[] args) {
        launch();
    }
}