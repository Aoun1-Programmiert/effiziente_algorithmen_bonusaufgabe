package src;


import src.Table.CalculateNeedlemanWunschTable;
import src.Table.Table;

public class Main {
    public static void main(String[] args) {
        String word1 = (args.length > 0) ? args[0] : "MOND";
        String word2 = (args.length > 1) ? args[1] : "DEO";

        Table table = CalculateNeedlemanWunschTable.calculateTable(word1, word2);
        table.printTable();


    }
}