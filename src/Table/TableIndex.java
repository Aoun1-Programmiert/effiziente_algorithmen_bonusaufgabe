package src.Table;

/**
 * Represents a position (i, j) in the alignment table.
 *
 * This is used to reference predecessor cells in the traceback process.
 */
public record TableIndex(int i, int j) {
    @Override
    public String toString() {
        return "[i: " + i + ", j: " + j + "]";
    }
}
