package src.Table;

public class TableValue {
    public int value;
    private final TableIndex[] predecessors;

    public TableValue(int value, TableIndex[] predecessor) {
        this.value = value;
        this.predecessors = predecessor;
    }

    public TableIndex[] getPredecessors() {
        return predecessors;
    }
}
