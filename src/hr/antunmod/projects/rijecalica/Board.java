package hr.antunmod.projects.rijecalica;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static hr.antunmod.projects.rijecalica.Main.foundWords;
import static hr.antunmod.projects.rijecalica.Main.map;
import static hr.antunmod.projects.rijecalica.Main.nLetterStrings;
import static hr.antunmod.projects.rijecalica.Main.words;

public class Board {

    private Field[][] fields;
    static final int NUMBER_OF_ROWS_AND_COLUMNS = 4;

    Board(Field[][] fields) {
        this.fields = fields;
    }

    void findWords() {
        for (int row = 0; row < NUMBER_OF_ROWS_AND_COLUMNS; ++row) {
            for (int col = 0; col < NUMBER_OF_ROWS_AND_COLUMNS; ++col) {
                Field field = fields[row][col];
                searchTheBoard(String.valueOf(field.getLetter()), field);
            }
        }
    }

    void findWordsUsingCharacterNodes() {
        for (int row = 0; row < NUMBER_OF_ROWS_AND_COLUMNS; ++row) {
            for (int col = 0; col < NUMBER_OF_ROWS_AND_COLUMNS; ++col) {
                Field field = fields[row][col];
                searchTheBoardUsingCharacterNodes(null, String.valueOf(field.getLetter()), field);
            }
        }
    }

    private void searchTheBoardUsingCharacterNodes(CharacterNode characterNode, String word, Field field) {
        if (searchIsDone()) {
            return;
        }

        int wordLength = word.length();
        field.setUsed(true);

        CharacterNode nextNode;
        char nextCharacter = word.charAt(wordLength - 1);

        if (wordLength > 1) {
            if (!characterNode.hasNextCharacter(nextCharacter)) {
                field.setUsed(false);
                return;
            }

            nextNode = characterNode.getNextCharacterNode(nextCharacter);

            if (nextNode.isEndCharacter() && word.length() >= 4) {
                foundWords.add(word);
            }
        } else {
            nextNode = map.get(nextCharacter);
        }

        List<Field> neighbours = generateNeighbours(field);
        for (Field neighbour : neighbours) {
            searchTheBoardUsingCharacterNodes(nextNode, word + String.valueOf(neighbour.getLetter()), neighbour);
        }

        field.setUsed(false);
    }

    private void searchTheBoard(String word, Field field) {
        if (searchIsDone()) {
            return;
        }

        int wordLength = word.length();
        field.setUsed(true);

        if (wordLength > 1) {
            TreeSet<String> nLetterString = nLetterStrings.get(wordLength - 2);
            if (!nLetterString.contains(word)) {
                field.setUsed(false);
                return;
            }

            if (words.contains(word) && word.length() >= 4) {
                foundWords.add(word);
            }
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

    private ArrayList<Field> generateNeighbours(Field field) {
        int row = field.getRow();
        int col = field.getCol();
        ArrayList<Field> neighbourFieldList = new ArrayList<>();

        for (int tmpRow = row - 1; tmpRow <= row + 1; ++tmpRow) {
            for (int tmpCol = col - 1; tmpCol <= col + 1; ++tmpCol) {
                if (fieldIsInsideRange(tmpRow, tmpCol) && !fields[tmpRow][tmpCol].isUsed()) {
                    neighbourFieldList.add(fields[tmpRow][tmpCol]);
                }

            }
        }
        return neighbourFieldList;
    }

    private boolean fieldIsInsideRange(int row, int col) {
        return row >= 0 && row < NUMBER_OF_ROWS_AND_COLUMNS && col >= 0 && col < NUMBER_OF_ROWS_AND_COLUMNS;
    }

}
