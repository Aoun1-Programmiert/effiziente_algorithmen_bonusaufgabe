package src.Solution;

import src.Table.TableIndex;
import src.Table.TableValue;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    private final List<TableIndex> indexList;
    private final List<Alignment> alignments;
    private int value = -1;

    public Solution(TableIndex index, int _value) {
        indexList = List.of(index);
        alignments = new ArrayList<>();
        value = _value;
    }

    public Solution(List<TableIndex> _indexList, List<Alignment> _alignments, int _value) {
        indexList = _indexList;
        alignments = _alignments;
        value = _value;
    }

    public Solution(Solution solution) {
        this.indexList = solution.indexList;
        this.alignments = solution.alignments;
        this.value = solution.value;
    }

    public Solution() {
        indexList = new ArrayList<TableIndex>();
        alignments = new ArrayList<>();
    }

    public List<TableIndex> getIndexList() {
        return indexList;
    }

    public void addIndex(TableIndex index) {
        indexList.add(index);
    }

    public List<Alignment> getAlignments() {
        return alignments;
    }

    public int getValue() {
        return value;
    }


}
