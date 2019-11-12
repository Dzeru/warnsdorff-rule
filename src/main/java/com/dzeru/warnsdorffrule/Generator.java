package com.dzeru.warnsdorffrule;

public class Generator {

    public static int CELL_COUNT = 9;
    public static String[] LETTERS = {" ", "A", "B", "C", "D", "E", "F", "G", "H"};

    public Object[][] cellValues = initCellValues();
    public boolean[][] visited = initVisited();

    public static Object[][] initCellValues() {
        String[][] cellValues = new String[CELL_COUNT][CELL_COUNT];

        for(int i = 0; i < CELL_COUNT; i++) {
            cellValues[i][0] = String.valueOf(i);
            cellValues[0][i] = LETTERS[i];
        }

        for(int i = 1; i < CELL_COUNT; i++) {
            for(int k = 1; k < CELL_COUNT; k++) {

                int[] s1 = {i + 1, k + 2};
                int[] s2 = {i - 1, k - 2};
                int[] s3 = {i + 1, k - 2};
                int[] s4 = {i - 1, k + 2};

                int[] s5 = {i + 2, k + 1};
                int[] s6 = {i - 2, k - 1};
                int[] s7 = {i + 2, k - 1};
                int[] s8 = {i - 2, k + 1};

                int[][] posCells = {s1, s2, s3, s4, s5, s6, s7, s8};

                int c = 0;

                for(int q = 0; q < posCells.length; q++) {
                    if(initPossible(posCells[q])) {
                        c++;
                    }
                }

                cellValues[i][k] = String.valueOf(c);
            }
        }

        return cellValues;
    }

    public static boolean[][] initVisited() {
        boolean[][] visited = new boolean[CELL_COUNT][CELL_COUNT];

        for(int i = 0; i < CELL_COUNT; i++) {
            for(int k = 0; k < CELL_COUNT; k++) {
                visited[i][k] = false;
            }
        }
        return visited;
    }

    public Object[][] generateCellValues() {
        for(int i = 1; i < CELL_COUNT; i++) {
            for(int k = 1; k < CELL_COUNT; k++) {
                int[] s1 = {i + 1, k + 2};
                int[] s2 = {i - 1, k - 2};
                int[] s3 = {i + 1, k - 2};
                int[] s4 = {i - 1, k + 2};

                int[] s5 = {i + 2, k + 1};
                int[] s6 = {i - 2, k - 1};
                int[] s7 = {i + 2, k - 1};
                int[] s8 = {i - 2, k + 1};

                int[][] posCells = {s1, s2, s3, s4, s5, s6, s7, s8};
                int c = 0;

                for(int q = 0; q < posCells.length; q++) {
                    if(possible(posCells[q])) {
                        c++;
                    }
                }

                cellValues[i][k] = String.valueOf(c);
            }
        }

        return cellValues;
    }

    public boolean possible(int[] s) {
        return s[0] > 0 && s[0] < 9 && s[1] > 0 && s[1] < 9 && !visited[s[0]][s[1]];
    }

    private static boolean initPossible(int[] s) {
        return s[0] > 0 && s[0] < 9 && s[1] > 0 && s[1] < 9;
    }
}
