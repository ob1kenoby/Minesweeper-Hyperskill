package minesweeper;

import java.util.Arrays;
import java.util.Random;

public class Field {
    private int[][] field;
    private boolean[][] fog;
    private boolean[][] flag;
    private int mines;
    private boolean minesPlaced = false;

    public Field(int size, int mines) {
        this.field = new int[size][size];
        this.mines = mines;
        this.fog = new boolean[size][size];
        this.flag = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(fog[i], true);
            Arrays.fill(flag[i], false);
        }
    }

    boolean hasMinesLeft() {
        return mines > 0;
    }

    void printField() {
        StringBuilder output = new StringBuilder(" |123456789|\n—│—————————│\n");
        for (int y = 0; y < field.length; y++) {
            output.append(y + 1);
            output.append("|");
            for (int x = 0; x < field[y].length; x++) {
                if (flag[y][x]) {
                    output.append('*');
                } else if (fog[y][x]) {
                    output.append('.');
                } else if (field[y][x] == 0) {
                    output.append('/');
                } else if (field[y][x] >= 10) {
                    output.append('X');
                } else {
                    output.append(field[y][x]);
                }
            }
            output.append("|\n");
        }
        output.append("—│—————————│");
        System.out.println(output.toString());
    }

    private boolean fillFieldWithMines(int... coordinatesOfFirstMove) {
        int xOfFirstMove = coordinatesOfFirstMove[0];
        int yOfFirstMove = coordinatesOfFirstMove[1];
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < mines) {
            int x = random.nextInt(9);
            int y = random.nextInt(9);
            if (field[y][x] < 9 && !(xOfFirstMove == x && yOfFirstMove == y)) {
                field[y][x] = 10;
                raiseSurroundings(x, y);
                minesPlaced++;
            }
        }
        return true;
    }

    private void raiseSurroundings(int x, int y) {
        int startX = getStart(x);
        int startY = getStart(y);
        int endX = getEnd(x);
        int endY = getEnd(y);
        for (int i = startY; i <= endY; i++) {
            for (int j = startX; j <= endX; j++) {
                if (field[i][j] < 9) {
                    field[i][j]++;
                }
            }
        }
    }

    private int getEnd(int i) {
        return (i == 8) ? 8 : i + 1;
    }

    private int getStart(int i) {
        return (i == 0) ? 0 : i - 1;
    }

    /**
     * Puts flag on a spot where mine is suspected.
     * If value of the spot equals:
     * 10 - there is a mine;
     * 0 - the spot is empty;
     * other value = amount of mines that are placed in the adjusted cells.
     * @param coordinates of the flag
     * @return true if the flag was placed and false if the flag wasn't placed.
     */
    boolean placeFlag(int[] coordinates) {
        int x = coordinates[0];
        int y = coordinates[1];
        if (fog[y][x]) {
            if (field[y][x] == 10) {
                if (flag[y][x]) {
                    mines++;
                } else {
                    mines--;
                }
            }
            flag[y][x] = !flag[y][x];
            return true;
        }
        return false;
    }

    /**
     *
     * @param coordinates of the click.
     * @return 1 - no mine, map was cleared from the fog;
     *         0 - there was a mine, kaboom;
     *         -1 - check on a flag or already cleared area.
     */
    int checkMine(int[] coordinates) {
        if (!minesPlaced) {
            minesPlaced = fillFieldWithMines(coordinates);
        }
        int x = coordinates[0];
        int y = coordinates[1];
        if (field[y][x] == 10) {
            field[y][x] = 11;
            clearWholeFieldFromFog();
            return 0;
        } else if (field[y][x] >= 0 && field[y][x] < 10) {
            clearFromFog(coordinates);
            return 1;
        }
        return -1;
    }

    private void clearWholeFieldFromFog() {
        for (boolean[] row : fog) {
            Arrays.fill(row, false);
        }
    }

    private void clearFromFog(int... coordinates) {
        int x = coordinates[0];
        int y = coordinates[1];
        int startX = getStart(x);
        int startY = getStart(y);
        int endX = getEnd(x);
        int endY = getEnd(y);
        for (int i = startY; i <= endY; i++) {
            for (int j = startX; j <= endX; j++) {
                if (fog[i][j] && field[i][j] == 0) {
                    fog[i][j] = false;
                    flag[i][j] = false;
                    clearFromFog(j, i);
                } else if (field[i][j] < 10) {
                    fog[i][j] = false;
                    flag[i][j] = false;
                }
            }
        }
    }

    /**
     *
     * @return if all the fog cells are cleared
     */
    boolean isNotOpen() {
        boolean everythingIsOpen = false;
        outerLoop:
        for (boolean[] row : fog) {
            for (boolean spot : row) {
                everythingIsOpen = !spot;
                if (!everythingIsOpen) {
                    break outerLoop;
                }
            }
        }
        return !everythingIsOpen;
    }
}
