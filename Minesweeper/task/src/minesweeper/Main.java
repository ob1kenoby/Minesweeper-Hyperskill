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

        while (mineField.hasMinesLeft()) {
            mineField.printField();
            System.out.print("Set/delete mines marks (x and y coordinates):");
            int[] coordinates = parseCoordinates(scanner.nextLine().split("\\s"));
            System.out.println();
            if (!mineField.putFlag(coordinates)) {
                System.out.println("There is a number here!");
            }
        }
        System.out.println("Congratulations! You found all mines!");
        scanner.close();
    }

    private static int[] parseCoordinates(String[] coordinates) {
        int[] parsedCoordinates = new int[coordinates.length];
        for (int i = 0; i < coordinates.length; i++) {
            parsedCoordinates[i] = Integer.parseInt(coordinates[i]) - 1;
        }
        return parsedCoordinates;
    }
}
