package src.Solution;

import src.Table.Table;
import src.Table.TableIndex;
import src.Table.TableValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Utility class for reconstructing all optimal alignment solutions
 * from a completed Needleman-Wunsch dynamic programming table.
 */
public class CalculateSolutionFromTable {

    /**
     * Initiates the backtracking process from the bottom-right cell
     * of the table to collect all possible optimal alignment solutions.
     *
     * @param table The dynamic programming table filled with alignment scores and predecessors
     * @return A list of all optimal alignment solutions
     */
    public static List<Solution> calculate(Table table) {
        return backtrack(table, table.width, table.height,
                new Solution(new TableIndex(table.width, table.height)));
    }

    /**
     * Recursive backtracking to follow predecessor pointers from (i, j)
     * to the top-left (0, 0), building complete alignment paths.
     *
     * @param table     The DP table
     * @param i         Current row index
     * @param j         Current column index
     * @param solution  Current partial solution being extended
     * @return A list of completed solutions traced from this cell
     */
    private static List<Solution> backtrack(Table table, int i, int j, Solution solution) {
        TableValue tableValue = table.getValue(i, j);

        // Base case: reached the start of the table
        if (i == 0 && j == 0) {
            //reversing both lists since we add from back to front
            Collections.reverse(solution.getIndexList());
            Collections.reverse(solution.getAlignments());
            return List.of(solution);
        }

        List<Solution> solutions = new ArrayList<>();
        TableIndex[] predecessors = tableValue.getPredecessors();

        for (TableIndex predecessor : predecessors) {
            // Copy current solution and extend it with predecessor info
            List<TableIndex> indexes = new ArrayList<>(solution.getIndexList());
            indexes.add(predecessor);

            List<Alignment> alignments = new ArrayList<>(solution.getAlignments());
            alignments.add(calculateAlignment(i, j, predecessor));

            Solution updatedSolution = new Solution(indexes, alignments);

            // Recurse back to the predecessor
            solutions.addAll(backtrack(table, predecessor.i(), predecessor.j(), updatedSolution));
        }

        return solutions;
    }

    /**
     * Determines the alignment type (match/mismatch or gap) based on
     * the difference between the current cell and its predecessor.
     *
     * @param i           Current row index
     * @param j           Current column index
     * @param predecessor The predecessor cell's index
     * @return The type of alignment operation (e.g., ALIGN, W1-Gap, W2-Gap)
     */
    private static Alignment calculateAlignment(int i, int j, TableIndex predecessor) {
        int shiftIdirection = i - predecessor.i();
        int shiftJdirection = j - predecessor.j();

        // Movement only in column direction => gap in word1
        if (shiftIdirection == 0) {
            return Alignment.ALIGN_W2_BLANK;
        }
        // Movement only in row direction => gap in word2
        else if (shiftJdirection == 0) {
            return Alignment.ALIGN_W1_BLANK;
        }
        // Diagonal movement => match or mismatch
        else {
            return Alignment.ALIGN;
        }
    }
}
