package src;


import src.Solution.CalculateSolutionFromTable;
import src.Solution.PrintAlignment;
import src.Solution.Solution;
import src.Table.CalculateNeedlemanWunschTable;
import src.Table.Table;
import src.Table.TableIndex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        String word1 = (args.length > 0) ? args[0] : "MOND";
        String word2 = (args.length > 1) ? args[1] : "DEO";

        Table table = CalculateNeedlemanWunschTable.calculateTable(word1, word2);
        table.printTable();

        List<Solution> solutions = CalculateSolutionFromTable.calculate(table);

        for (Solution solution : solutions) {
            PrintAlignment.alignment(table, solution);
        }
    }
}