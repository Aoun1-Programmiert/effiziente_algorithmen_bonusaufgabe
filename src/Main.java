package src;



public class Main {
    public static void main(String[] args) {
        String word1 = "DEO";
        String word2 = "MOND";

        Table table =CalculateNeedlemanWunschTable.calculateTable(word1, word2);
        table.printTable();

    }
}