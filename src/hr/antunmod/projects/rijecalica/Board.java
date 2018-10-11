package hr.antunmod.projects.rijecalica;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private Field[][] fields;
    static final int NUMBER_OF_ROWS_AND_COLUMNS = 4;

    Board(Field[][] fields) {
        this.fields = fields;
    }

    void searchTheBoard(String word, Field field) {
        if (searchIsDone()) {
            return;
        }

        field.setUsed(true);

        if (word.length() > 4) {
            Main.words.add(word);
        }

        List<Field> neighbours = generateNeighbours(field);
        for (Field neighbour : neighbours) {
            searchTheBoard(word + String.valueOf(neighbour.getLetter()), neighbour);
        }

        field.setUsed(false);

    }

    private boolean searchIsDone() {
        for (int row = NUMBER_OF_ROWS_AND_COLUMNS - 1; row >= 0; --row) {
            for (int col = NUMBER_OF_ROWS_AND_COLUMNS - 1; col >= 0; --col) {
                if (!fields[row][col].isUsed()) {
                    return false;
                }
            }
        }
        return true;
    }

    Field getFirstField() {
        return fields[0][0];
    }


    private ArrayList<Field> generateNeighbours(Field field) {
        int row = field.getRow();
        int col = field.getCol();
        ArrayList<Field> neighbourIndexList = new ArrayList<>();
        int tmpRow, tmpCol;

        tmpCol = col - 1;
        if (fieldIsInsideRange(row, tmpCol) && !fields[row][tmpCol].isUsed()) {
            neighbourIndexList.add(fields[row][tmpCol]);
        }

        tmpCol = col + 1;
        if (fieldIsInsideRange(row, tmpCol) && !fields[row][tmpCol].isUsed()) {
            neighbourIndexList.add(fields[row][tmpCol]);
        }

        tmpRow = row - 1;
        if (fieldIsInsideRange(tmpRow, col) && !fields[tmpRow][col].isUsed()) {
            neighbourIndexList.add(fields[tmpRow][col]);
        }

        tmpRow = row + 1;
        if (fieldIsInsideRange(tmpRow, col) && !fields[tmpRow][col].isUsed()) {
            neighbourIndexList.add(fields[tmpRow][col]);
        }

        return neighbourIndexList;
    }

    private boolean fieldIsInsideRange(int row, int col) {
        return row >= 0 && row < NUMBER_OF_ROWS_AND_COLUMNS && col >= 0 && col < NUMBER_OF_ROWS_AND_COLUMNS;
    }

}
