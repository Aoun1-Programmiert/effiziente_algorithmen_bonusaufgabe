package src;

public class TableValue {
    public int value;
    private final TableIndex[] predecessors;

    public TableValue(int value, TableIndex[] predecessor) {
        this.value = value;
        this.predecessors = predecessor;
    }

    public TableValue(TableValue tableValue) {
        this.value = tableValue.value;
        this.predecessors = tableValue.predecessors;
    }

    public TableIndex[] getPredecessors() {
        return predecessors;
    }
}
