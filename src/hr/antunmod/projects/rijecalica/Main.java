package hr.antunmod.projects.rijecalica;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class Main {

    static TreeSet<String> foundWords;
    static TreeSet<String> words;
    static List<TreeSet<String>> nLetterStrings = new ArrayList<>();
    static HashMap<Character, CharacterNode> map;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        words = new TreeSet<>(Files.readAllLines(Paths.get("words/updated.txt")));


        while (true) {
            try {
                loadWords();
            } catch (IOException e) {
                System.out.println("There was an arror reading input files!");
                return;
            }

            map = generateCharacterNodeMap();

            foundWords = new TreeSet<>();

            Field[][] fields = enterFields(scanner);
            Board board = new Board(fields);

            long startTime = System.nanoTime();
            board.findWordsUsingCharacterNodes();
            long endTime = System.nanoTime();
            System.out.println("Linked list time: " + (endTime - startTime)/1e9);
            System.out.println("Found words:");

            for (String word : foundWords) {
                System.out.println(word);
            }

            foundWords =  new TreeSet<>();

            startTime = System.nanoTime();
            board.findWords();
            endTime = System.nanoTime();
            System.out.println("Dictionary time: " + (endTime - startTime)/1e9);
            System.out.println("Found words:");

            for (String word : foundWords) {
                System.out.println(word);
            }

            deleteUnnecessaryWords(scanner);
            addNewWords(scanner);
            updateWords();
        }
//        scanner.close();
    }

    private static void loadWords() throws IOException {
        final int maxWordLength = 16;
        final String nLetterWordPath = "words/nLetterWords/";
        final String nLetterWordSuffix = "_letter_words.txt";

        for (int n = 2; n <= maxWordLength; ++n) {
            Path nLetterWordsPath = Paths.get(nLetterWordPath + n + nLetterWordSuffix);
            List<String> nLetterWords = Files.readAllLines(nLetterWordsPath);
            nLetterStrings.add(new TreeSet<>(nLetterWords));
        }

    }

    private static Field[][] enterFields(Scanner scanner) {
        final int numberOfRows = 4;
        Field[][] fields = new Field[numberOfRows][numberOfRows];

        System.out.println("Enter letters row by row from left to right, letter by letter");
        String input = scanner.nextLine();
        char[] inputArray = input.toCharArray();
        for (int row = 0; row < Board.NUMBER_OF_ROWS_AND_COLUMNS; ++row) {
            for (int col = 0; col < Board.NUMBER_OF_ROWS_AND_COLUMNS; ++col) {
                fields[row][col] = new Field(inputArray[row * numberOfRows + col], row, col);
            }
        }

        return fields;
    }

    private static void deleteUnnecessaryWords(Scanner scanner) {
        System.out.println("Enter words which were found in dictionary but weren't found in the game.");
        System.out.println("Terminate false words input by typing 'EXIT'.");

        String input;
        while (!(input = scanner.nextLine()).equals("EXIT")) {
            words.remove(input);
        }

        System.out.println("Stopped deleting false words.");

    }

    private static void updateWords() throws IOException {
        System.out.println("Updating words...");
        FileWriter writer = new FileWriter("words/updated.txt");

        for (String word : words) {
            writer.write(word + "\n");
        }

        updateNLetterWords();

        System.out.println("Done updating words.");
    }

    private static void updateNLetterWords() throws IOException {
        List<Integer> lengths = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        for (Integer length : lengths) {
            FileWriter nLetterWordsWriter = new FileWriter("words/nLetterWords/" + length + "_letter_words.txt");
            String previous = "";
            for (String word : words) {
                if (word.length() >= length) {
                    String lastWord = word.substring(0, length);
                    if (!previous.equals(lastWord)) {
                        previous = lastWord;
                        nLetterWordsWriter.write(lastWord + "\n");
                    }
                }
            }
            nLetterWordsWriter.close();
        }
    }

    private static void addNewWords(Scanner scanner) {
        System.out.println("Enter words which the program did not find:");
        System.out.println("Press EXIT to end word input.");

        String input;
        while (!(input = scanner.nextLine()).equals("EXIT")) {
            words.add(input);
        }

        System.out.println("Done adding new words.");
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

    private static HashMap<Character, CharacterNode> generateCharacterNodeMap() {
        char[] letters = {'a', 'b', 'c', 'č', 'ć', 'd', 'đ', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'r', 's', 'š', 't', 'u', 'v', 'z', 'ž'};
        HashMap<Character, CharacterNode> characterNodeMap = new HashMap<>();

        for (char letter : letters) {
            characterNodeMap.put(letter, new CharacterNode(letter));
        }

        for (String word : words) {
            CharacterNode node = characterNodeMap.get(word.charAt(0));
            int counter = 1;
            while (counter < word.length()) {
                char nextChar = word.charAt(counter);
                if (!node.hasNextCharacter(nextChar)) {
                    node.addCharacter(nextChar);
                }
                counter++;
                node = node.getNextCharacterNode(nextChar);

                if (counter == word.length()) {
                    node.setEndCharacter(true);
                }
            }
        }
        return characterNodeMap;
    }
}
