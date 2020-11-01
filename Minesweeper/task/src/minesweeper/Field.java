package minesweeper;

import java.util.Arrays;
import java.util.Random;

public class Field {
    private int[][] field;
    private boolean[][] fog;
    private int mines;

    public Field(int size, int mines) {
        this.field = new int[size][size];
        this.mines = mines;
        this.fog = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(this.fog[i], false);
        }
        placeMines();
    }

    boolean hasMinesLeft() {
        return mines > 0;
    }

    void printField() {
        StringBuilder output = new StringBuilder(" |123456789|\n—│—————————│\n");
        for (int i = 0; i < this.field.length; i++) {
            output.append((i + 1) + "|");
            for (int spot : this.field[i]) {
                if (spot == 0 || spot == 10) {
                    output.append('.');
                } else if (spot < 0) {
                    output.append('*');
                } else {
                    output.append(spot);
                }
            }
            output.append("|\n");
        }
        output.append("—│—————————│");
        System.out.println(output.toString());
    }

    void placeMines() {
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < mines) {
            int x = random.nextInt(9);
            int y = random.nextInt(9);
            if (field[x][y] < 9) {
                field[x][y] = 10;
                raiseSurroundings(x, y);
                minesPlaced++;
            }
        }
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

    public boolean putFlag(String[] coordinates) {
        int x = Integer.parseInt(coordinates[1]) - 1;
        int y = Integer.parseInt(coordinates[0]) - 1;
        if (field[x][y] == 10) {
            field[x][y] = -1;
            mines--;
        } else if (field[x][y] == 0) {
            field[x][y] = -2;
        } else if (field[x][y] == -2) {
            field[x][y] = 0;
        } else if (field[x][y] == -1) {
            field[x][y] = 10;
            mines++;
        } else {
            return false;
        }
        return true;
    }
}
