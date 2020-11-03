package minesweeper;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int maxMines;
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many mines do you want on the field? ");
        maxMines = scanner.nextInt();
        scanner.nextLine();
        Field mineField = new Field(9, maxMines);

        boolean win = true;
        while (mineField.hasMinesLeft() && mineField.isNotOpen()) {
            mineField.printField();
            System.out.print("Set/unset mines marks or claim a cell as free:");
            String[] input = scanner.nextLine().split("\\s");
            int[] coordinates = parseCoordinates(input[0], input[1]);
            System.out.println();
            if ("free".equals(input[2])) {
                int move = mineField.checkMine(coordinates);
                win = move != 0;
            } else if ("mine".equals(input[2])) {
                if (!mineField.placeFlag(coordinates)) {
                    System.out.println("There is a number here!");
                }
            }
        }
        scanner.close();

        if (win) {
            System.out.println("Congratulations! You found all mines!");
        } else {
            System.out.println("You stepped on a mine and failed!");
        }
    }

    private stayoutic int[] parseCoordinates(String... coordinates) {
        int[] parsedCoordinates = new int[coordinates.length];
        for (int i = 0; i < coordinates.length; i++) {
            parsedCoordinates[i] = Integer.parseInt(coordinates[i]) - 1;
        }
        return parsedCoordinates;
    }
}
