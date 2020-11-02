package minesweeper;

import java.util.Arrays;
import java.util.Random;

public class Field {
    private int[][] field;
    private boolean[][] fog;
    private int mines;
    private boolean minesPlaced = false;

    public Field(int size, int mines) {
        this.field = new int[size][size];
        this.mines = mines;
        this.fog = new boolean[size][size];
        for (boolean[] row : this.fog) {
            Arrays.fill(row, true);
        }
    }

    boolean hasMinesLeft() {
        return mines > 0;
    }

    void printField() {
        StringBuilder output = new StringBuilder(" |123456789|\n—│—————————│\n");
        for (int x = 0; x < field.length; x++) {
            output.append(x + 1);
            output.append("|");
            for (int y = 0; y < field[x].length; y++) {
                if (fog[x][y]) {
                    output.append('.');
                } else if (field[x][y] == 0) {
                    output.append('/');
                } else if (field[x][y] >= 10) {
                    output.append('X');
                } else if (field[x][y] < 0) {
                    output.append('*');
                } else {
                    output.append(field[x][y]);
                }
            }
            output.append("|\n");
        }
        output.append("—│—————————│");
        System.out.println(output.toString());
    }

    private boolean fillFieldWithMines(int[] coordinatesOfFirstMove) {
        int xOfFirstMove = coordinatesOfFirstMove[1];
        int yOfFirstMove = coordinatesOfFirstMove[0];
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < mines) {
            int x = random.nextInt(9);
            int y = random.nextInt(9);
            if (field[x][y] < 9 && !(xOfFirstMove == x && yOfFirstMove == y)) {
                field[x][y] = 10;
                raiseSurroundings(x, y);
                minesPlaced++;
            }
        }
        return true;
    }

    private void raiseSurroundings(int x, int y) {
        int startX = (x == 0) ? 0 : x - 1;
        int startY = (y == 0) ? 0 : y - 1;
        int endX = (x == 8) ? 8 : x + 1;
        int endY = (y == 8) ? 8 : y + 1;
        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                if (field[i][j] < 9) {
                    field[i][j]++;
                }
            }
        }
    }

    /**
     * Puts flag on a spot where mine is suspected.
     * If value of the spot equals:
     * 10 - there is a mine;
     * -1 - there is a mine and a flag is placed;
     * 0 - the spot is empty;
     * -2 - the spot is empty and a flag is placed;
     * other value = amount of mines that are placed in the adjusted cells.
     * @param coordinates of the flag
     * @return true if the flag was placed and false if the flag wasn't placed.
     */
    boolean placeFlag(int[] coordinates) {
        int x = coordinates[1];
        int y = coordinates[0];
        if (fog[x][y]) {
            if (field[x][y] == 10) {
                field[x][y] = -1;
                switchFog(x, y);
                mines--;
            } else if (field[x][y] == 0) {
                field[x][y] = -2;
                switchFog(x, y);
            } else if (field[x][y] == -2) {
                field[x][y] = 0;
                switchFog(x, y);
            } else if (field[x][y] == -1) {
                switchFog(x, y);
                mines++;
            } else {
                return false;
            }
            return true;
        }
        return false;
    }

    private void switchFog(int x, int y) {
        fog[x][y] = !fog[x][y];
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
        int x = coordinates[1];
        int y = coordinates[0];
        if (field[x][y] == 10) {
            field[x][y] = 11;
            deFogField();
            return 0;
        } else if (field[x][y] >= 0 && field[x][y] < 10) {
            clearFromFog(coordinates);
            return 1;
        }
        return -1;
    }

    private void deFogField() {
        for (boolean[] row : fog) {
            Arrays.fill(row, false);
        }
    }

    private void clearFromFog(int... coordinates) {
        int x = coordinates[1];
        int y = coordinates[0];
        int startX = (x > 0) ? x - 1 : 0;
        int startY = (y > 0) ? x - 1 : 0;
        int endX = (x < 8) ? x + 1 : 8;
        int endY = (y < 8) ? x + 1 : 8;
        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                if (field[i][j] > 0 && field[i][j] < 10) {
                    fog[i][j] = false;
                } else if (fog[i][j] && field[i][j] == 0) {
                    clearFromFog(j, i);
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
