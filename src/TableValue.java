package src;

public class TableValue {
    public int value;
    public int[] predecessor;

    public TableValue(int value, int[] predecessor) {
        this.value = value;
        this.predecessor = predecessor;
    }

    public TableValue(TableValue tableValue) {
        this.value = tableValue.value;
        this.predecessor = tableValue.predecessor;
    }

}
