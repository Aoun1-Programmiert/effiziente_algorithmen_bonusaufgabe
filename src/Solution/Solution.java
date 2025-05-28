package src.Solution;

import src.Table.TableIndex;
import src.Table.TableValue;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    private final List<TableIndex> indexList;
    private final List<Alignment> alignments;

    public Solution(TableIndex index) {
        indexList = List.of(index);
        alignments = new ArrayList<>();
    }

    public Solution(List<TableIndex> _indexList, List<Alignment> _alignments) {
        indexList = _indexList;
        alignments = _alignments;
    }

    public Solution(Solution solution) {
        this.indexList = solution.indexList;
        this.alignments = solution.alignments;
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

}
