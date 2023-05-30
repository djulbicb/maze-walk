package com.example.mazewalk.simple;

import com.example.mazewalk.Application;
import javafx.scene.layout.AnchorPane;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid {
    private int rows;
    private int columns;

    private Cell[][] grid;

    public Cell[][] getGrid() {
        return grid;
    }

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        grid = new Cell[rows][columns];

        fillGrid();
        configureCells();
    }

    private void configureCells() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell currentCell = grid[i][j];

                Cell east = findCell(i, j+1);
                Cell south = findCell(i+1, j);
                Cell west = findCell(i, j-1);
                Cell north = findCell(i-1, j);

                currentCell.setEast(east);
                currentCell.setSouth(south);
                currentCell.setWest(west);
                currentCell.setNorth(north);

            }
        }
    }

    private Cell findCell(int rowIdx, int colIdx) {
        if (rowIdx < 0 || colIdx < 0 || rowIdx>=rows || colIdx>=columns) {
            return null;
        }
        return grid[rowIdx][colIdx];
    }


    public void savePng() {
        int size = Application.SIZE;
        int imgWidth = columns * size;
        int imgHeight = rows * size;

        BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Set background color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, imgWidth, imgHeight);

        // Set stroke properties
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5.0f));

        // Create a Path2D object for the polyline
        Path2D.Double polyline = new Path2D.Double();
        polyline.moveTo(0, 0); // Move to the first point
        polyline.lineTo(imgWidth, 0); // Line to the second point
        polyline.lineTo(imgWidth, imgHeight); // Line to the third point
        polyline.lineTo(0, imgHeight); // Line to the third point
        polyline.closePath(); // Close the path (optional)
        g2d.draw(polyline);

        for (Cell[] row : grid) {
            for (Cell col : row) {
                int columnIdx = col.getRowIdx();
                int rowIdx = col.getColumnIdx();

                int x = rowIdx * size;
                int y = columnIdx * size;

                if (!col.isLinkToNorth()) {
                    polyline.moveTo(x, y);
                    polyline.lineTo(x + size, y);
                }
                if (!col.isLinkToSouth()) {
                    polyline.moveTo(x, y+size);
                    polyline.lineTo(x + size, y+size);
                }
                if (!col.isLinkToEast()) {
                    polyline.moveTo(x + size, y);
                    polyline.lineTo(x + size, y+size);
                }
                if (!col.isLinkToWest()) {
                    polyline.moveTo(x, y);
                    polyline.lineTo(x, y+size);
                }

            }
        }
        g2d.draw(polyline);

        File outputFile = new File("image.png");
        try {
            ImageIO.write(image, "PNG", outputFile);
            System.out.println("Image saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String toAscii() {
        // Ascii elements
        String body = "  ";
        String side = "|";
        String corner="+";
        String top="---";
        String newLine = "\n";
        String empty = " ";
        // Create top and bottom border
        StringBuilder border = new StringBuilder();
        for (Cell[] row : grid) {
            for (Cell column : row) {
                border.append(corner + top);
            }
            border.append(corner);
            border.append(newLine);
            break;
        }

        // Render
        ///////////////////////////////////////
        StringBuilder out = new StringBuilder();

        // append top border
        out.append(border);

        // append body
        for (int i = 0; i < rows; i++) {
            out.append(side);
            for (int j = 0; j < columns; j++) {
                if (grid[i][j].isLinkToEast()) {
                    out.append(empty + body + empty);
                } else {
                    out.append(empty + body + side);
                }
            }
            out.append(newLine);

            // skip drawing bottom last line
            if (i == rows - 1) {
                continue;
            }

            for (int j = 0; j < columns; j++) {
                if (grid[i][j].isLinkToSouth()) {
                    out.append(corner + body + empty);
                } else {
                    out.append(corner + top);
                }
            }
            out.append(side);
            out.append("\n");
        }

        // append bottom border
        out.append(border.toString());

        return out.toString().trim();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cell[] row : grid) {
            for (Cell column : row) {
                sb.append(column);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private void fillGrid(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    public List<AnchorPane> render() {
        List<AnchorPane> cellList = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cellList.add(grid[i][j].render());
            }
        }
        return cellList;
    }

    public Cell getRandomCell() {
        Random random = new Random();
        return grid[random.nextInt(rows)][random.nextInt(columns)];
    }

    public void draw(List<Cell> cells) {
    }
}
