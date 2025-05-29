package src.Solution;

import src.Table.Table;

import java.util.Collections;
import java.util.List;

/**
 * Utility class for printing and displaying solutions to sequence alignment problems.
 * This class provides methods to format and output alignment solutions in a readable format,
 * showing how two sequences can be aligned with gaps and matches.
 */
public class PrintSolution {

    /**
     * Prints all solutions for a sequence alignment problem to the console.
     * Displays the total number of solutions, the optimal value, and each individual
     * alignment solution with its visual representation.
     *
     * @param table The dynamic programming table containing the alignment problem data,
     *              including the two sequences to be aligned and the optimal solution value
     * @param solutions A list of all optimal solutions found for the alignment problem.
     *                  Each solution contains the alignment operations and index paths.
     *
     * @throws NullPointerException if table or solutions is null
     *
     * @see #alignment(Table, Solution)
     */
    public static void printSolution(Table table, List<Solution> solutions) {
        System.out.println("---------------------");
        System.out.println("Solution:");
        System.out.println("Amount: " + solutions.size()
                + " - Value: " + table.getValueOfOptSolution());
        for (Solution solution : solutions) {
            System.out.println("---------------------");
            PrintSolution.alignment(table, solution);
        }
    }

    /**
     * Creates and displays a visual representation of a single alignment solution.
     * Shows how the two sequences are aligned by displaying them with gaps (represented by 'X')
     * and separators ('|') between each position.
     *
     * <p>The method processes three types of alignment operations:
     * <ul>
     * <li>ALIGN: Both characters are aligned (match or mismatch)</li>
     * <li>ALIGN_W1_BLANK: Character from first sequence aligned with gap in second sequence</li>
     * <li>ALIGN_W2_BLANK: Character from second sequence aligned with gap in first sequence</li>
     * </ul>
     *
     * <p>Note: This method modifies the input solution by reversing its index list and alignments
     * to display them in the correct order.
     *
     * @param table The table containing the two sequences (word1 and word2) to be aligned
     * @param solution The solution containing the sequence of alignment operations.
     *                 The solution's lists will be reversed as a side effect of this method.
     *
     * @throws NullPointerException if table, solution, or any of their required fields are null
     * @throws IndexOutOfBoundsException if the alignment operations reference positions
     *                                   beyond the bounds of the input sequences
     *
     * @see Alignment
     * @see Solution#getAlignments()
     * @see Solution#getIndexList()
     */
    public static void alignment(Table table, Solution solution){

        Collections.reverse(solution.getIndexList());
        Collections.reverse(solution.getAlignments());

        String w1 = table.word1;
        String w2 = table.word2;
        StringBuilder alignmentW1 = new StringBuilder();
        StringBuilder alignmentW2 = new StringBuilder();
        int w1IndexCounter = 0;
        int w2IndexCounter = 0;

        for (Alignment alignment : solution.getAlignments()) {
            switch (alignment) {
                case ALIGN -> {
                    alignmentW1.append(w1.charAt(w1IndexCounter));
                    alignmentW2.append(w2.charAt(w2IndexCounter));
                    w1IndexCounter++;
                    w2IndexCounter++;
                }
                case ALIGN_W1_BLANK -> {
                    alignmentW1.append(w1.charAt(w1IndexCounter));
                    w1IndexCounter++;
                    alignmentW2.append("X");
                }
                case ALIGN_W2_BLANK -> {
                    alignmentW2.append(w2.charAt(w2IndexCounter));
                    w2IndexCounter++;
                    alignmentW1.append("X");
                }
            }
            alignmentW1.append("|");
            alignmentW2.append("|");
        }
        System.out.println(alignmentW1.toString());
        System.out.println(alignmentW2.toString());
    }
}