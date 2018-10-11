package hr.antunmod.projects.rijecalica;

import java.util.Scanner;
import java.util.TreeSet;

public class Main {

    static TreeSet<String> words = new TreeSet<>();

    public static void main(String[] args) {
        while (true) {
            Field[][] fields = generateTestFields();
            Board board = new Board(fields);

            Field firstField = board.getFirstField();
            board.searchTheBoard(String.valueOf(firstField.getLetter()), firstField);
        }
    }

    private static Field[][] enterFields() {
        Scanner scanner = new Scanner(System.in);
        Field[][] fields = new Field[4][4];

        System.out.println("Enter letters row by row from left to right, letter by letter");

        for (int row = 0; row < Board.NUMBER_OF_ROWS_AND_COLUMNS; ++row) {
            for (int col = 0; col < Board.NUMBER_OF_ROWS_AND_COLUMNS; ++col) {
                fields[row][col] = new Field(scanner.next().charAt(0), row, col);
            }
        }

        return fields;
    }

    private static Field[][] generateTestFields() {
        Field[][] fields = new Field[4][4];
        char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'č', 'ć'};

        for (int row = 0; row < Board.NUMBER_OF_ROWS_AND_COLUMNS; ++row) {
            for (int col = 0; col < Board.NUMBER_OF_ROWS_AND_COLUMNS; ++col) {
                fields[row][col] = new Field(letters[row * Board.NUMBER_OF_ROWS_AND_COLUMNS + col], row, col);
            }
        }

        return fields;
    }
}
