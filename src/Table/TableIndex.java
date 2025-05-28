package src.Table;

public record TableIndex(int i, int j) {
    @Override
    public String toString() {
        return "[i: " + i + ", j: " + j + "]";
    }
}
