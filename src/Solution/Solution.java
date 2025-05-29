package src.Solution;

import src.Table.TableIndex;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a traceback result in the Needleman-Wunsch alignment.
 *
 * A Solution object holds:
 * - A list of indices used in the traceback path through the DP table.
 * - A list of alignments (e.g., matched/mismatched characters or gaps).
 */
public class Solution {

    // Stores the path of indices used during traceback
    private final List<TableIndex> indexList;

    // Stores the resulting alignment information (symbols from both words)
    private final List<Alignment> alignments;

    /**
     * Creates a solution initialized with a single starting index.
     *
     * @param index the starting index of the traceback path
     */
    public Solution(TableIndex index) {
        indexList = new ArrayList<>();
        indexList.add(index);
        alignments = new ArrayList<>();
    }

    /**
     * Creates a solution from given index and alignment lists.
     *
     * @param _indexList   the traceback path
     * @param _alignments  the alignment operations
     */
    public Solution(List<TableIndex> _indexList, List<Alignment> _alignments) {
        indexList = _indexList;
        alignments = _alignments;
    }

    /**
     * Copy constructor (shallow copy). Be cautious: the lists are shared.
     *
     * @param solution the solution to copy
     */
    public Solution(Solution solution) {
        this.indexList = solution.indexList;
        this.alignments = solution.alignments;
    }

    /**
     * Creates an empty solution with no indices or alignments.
     */
    public Solution() {
        indexList = new ArrayList<>();
        alignments = new ArrayList<>();
    }

    /**
     * Returns the list of table indices in the traceback path.
     *
     * @return list of TableIndex objects
     */
    public List<TableIndex> getIndexList() {
        return indexList;
    }

    /**
     * Adds a table index to the traceback path.
     *
     * @param index the table index to add
     */
    public void addIndex(TableIndex index) {
        indexList.add(index);
    }

    /**
     * Returns the list of alignment operations (characters/gaps).
     *
     * @return list of Alignment objects
     */
    public List<Alignment> getAlignments() {
        return alignments;
    }
}
