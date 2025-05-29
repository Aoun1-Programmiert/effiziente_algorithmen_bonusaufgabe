package src;


import src.Solution.CalculateSolutionFromTable;
import src.Solution.PrintSolution;
import src.Solution.Solution;
import src.Table.CalculateNeedlemanWunschTable;
import src.Table.Table;

import java.util.List;

/**
 * Entry point of the program.
 *
 * This class initializes two strings, computes their alignment using the
 * Needleman-Wunsch algorithm, and prints both the scoring table and all possible
 * optimal alignment solutions.
 */
public class Main {
    public static void main(String[] args) {
        // Use provided command line arguments or fall back to default strings
        String word1 = (args.length > 0) ? args[0] : "MOND";
        String word2 = (args.length > 1) ? args[1] : "DEO";

        // Compute the dynamic programming table for the two input strings
        Table table = CalculateNeedlemanWunschTable.calculateTable(word1, word2);

        // Print the filled dynamic programming table
        table.printTable();

        // Extract all optimal alignments from the filled table (traceback phase)
        List<Solution> solutions = CalculateSolutionFromTable.calculate(table);

        // Print each alignment solution in a human-readable format
        PrintSolution.printSolution(table, solutions);
    }
}