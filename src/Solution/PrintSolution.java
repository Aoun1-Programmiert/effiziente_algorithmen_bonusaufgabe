package src.Solution;

import src.Table.Table;

import java.util.Collections;
import java.util.List;

/**
 * [i: 0, j: 0]
 * [i: 0, j: 1]
 * [i: 0, j: 2]
 * [i: 1, j: 2]
 * [i: 2, j: 3]
 * [i: 3, j: 3]
 * [i: 4, j: 3]
 */

public class PrintSolution {
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
