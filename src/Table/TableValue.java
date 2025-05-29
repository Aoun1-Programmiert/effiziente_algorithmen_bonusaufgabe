package src.Table;

/**
 * Represents a single cell in the Needleman-Wunsch table.
 *
 * Each TableValue stores the computed alignment score and the indices
 * of predecessor cells that led to this score (used for backtracking).
 */
public class TableValue {
    public int value; // alignment score for this cell
    private final TableIndex[] predecessors;

    /**
     * Constructs a TableValue.
     *
     * @param value        the alignment score
     * @param predecessor  the cells that led to this value (for backtracking)
     */
    public TableValue(int value, TableIndex[] predecessor) {
        this.value = value;
        this.predecessors = predecessor;
    }

    /**
     * Returns the predecessor cells of this value.
     *
     * @return array of TableIndex objects that led to this value
     */
    public TableIndex[] getPredecessors() {
        return predecessors;
    }
}
