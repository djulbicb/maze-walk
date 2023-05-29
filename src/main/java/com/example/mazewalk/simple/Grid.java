package com.example.mazewalk.simple;

import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

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
                if (grid[i][j].goesEast()) {
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
                if (grid[i][j].goesSouth()) {
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
}
