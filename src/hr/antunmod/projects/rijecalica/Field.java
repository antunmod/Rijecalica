package hr.antunmod.projects.rijecalica;

public class Field {

    private boolean used;
    private char letter;
    private Index index;

    Field(char letter, int row, int col) {
        this.letter = letter;
        this.index = new Index(row, col);
    }

    boolean isUsed() {
        return used;
    }

    void setUsed(boolean used) {
        this.used = used;
    }

    char getLetter() {
        return letter;
    }

    int getRow() {
        return index.getRow();
    }

    int getCol() {
        return index.getCol();
    }

    public Index getIndex() {
        return index;
    }
}
