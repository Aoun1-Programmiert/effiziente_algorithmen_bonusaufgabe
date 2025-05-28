package src.Solution;

import src.Table.Table;
import src.Table.TableIndex;
import src.Table.TableValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculateSolutionFromTable {
    public static List<Solution> calculate(Table table) {
        return backtrack(table, table.width, table.height,
                new Solution(new TableIndex(table.width, table.height), table.getValueOfOptSolution()));
    }

    /**
     *
     *
     * @param table to iterate through
     * @param i current position
     * @param j current position
     * @return
     */
    private static List<Solution> backtrack(Table table, int i, int j, Solution solution) {
        TableValue tableValue = table.getValue(i, j);
        if(i == 0 && j == 0){//we finished
            return List.of(solution);
        }
        List<Solution> solutions = new ArrayList<>();
        TableIndex[] predecessors = tableValue.getPredecessors();

        for (TableIndex predecessor : predecessors) {
            List<TableIndex> indexes = new ArrayList<>(solution.getIndexList());
            indexes.add(predecessor);
            List<Alignment> alignments = new ArrayList<>(solution.getAlignments());
            alignments.add(calculateAlignment(i,j, predecessor));

            Solution updatedSolution = new Solution(indexes, alignments, solution.getValue());

            solutions.addAll(backtrack(table, predecessor.i(), predecessor.j(), updatedSolution));
        }
        return solutions;
    }

    private static Alignment calculateAlignment(int i, int j, TableIndex predecessor) {
        int shiftIdirection = i - predecessor.i();
        int shiftJdirection = j - predecessor.j();

        if(shiftIdirection != shiftJdirection){
            if(shiftIdirection == 0){
                return Alignment.ALIGN_W2_BLANK;
            } else {
                return Alignment.ALIGN_W1_BLANK;
            }
        } else {
            return Alignment.ALIGN;
        }

    }

}
