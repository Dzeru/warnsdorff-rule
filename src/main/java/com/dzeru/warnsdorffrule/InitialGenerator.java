package com.dzeru.warnsdorffrule;

public class InitialGenerator {

    public static int CELL_COUNT = 9;

    public static String[] LETTERS = {" ", "A", "B", "C", "D", "E", "F", "G", "H"};

    public static Object[][] generateCellValues() {
        String[][] cellValues = new String[CELL_COUNT][CELL_COUNT];

        for(int i = 0; i < CELL_COUNT; i++) {
            cellValues[0][i] = LETTERS[i];
            cellValues[i][0] = LETTERS[i];
        }

        for(int i = 1; i < CELL_COUNT; i++) {
            cellValues[i][0] = LETTERS[i];
            for(int k = 1; k < CELL_COUNT; k++) {
                cellValues[i][k] = "6";
            }
        }

        return cellValues;
    }

    public static boolean[][] generateVisited() {
        boolean[][] visited = new boolean[CELL_COUNT][CELL_COUNT];

        for(int i = 0; i < CELL_COUNT; i++) {
            for(int k = 0; k < CELL_COUNT; k++) {
                visited[i][k] = false;
            }
        }
        return visited;
    }
}
