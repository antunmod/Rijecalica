package hr.antunmod.projects.rijecalica;

import java.util.HashMap;

public class CharacterNode {

    private char character;
    private boolean endCharacter;
    private HashMap<Character, CharacterNode> nextCharacters;

    public CharacterNode(char character) {
        this.character = character;
    }

    public boolean isEndCharacter() {
        return endCharacter;
    }
    public void setEndCharacter(boolean isEndCharacter) {
        this.endCharacter = isEndCharacter;
    }

    public boolean hasNextCharacter(char character) {
        if (nextCharacters == null) {
            nextCharacters = new HashMap<>();
            return false;
        }
        return nextCharacters.containsKey(character);
    }

    public void addCharacter(char character) {
        nextCharacters.put(character, new CharacterNode(character));
    }

    public CharacterNode getNextCharacterNode(char character) {
        return nextCharacters.get(character);
    }
}
