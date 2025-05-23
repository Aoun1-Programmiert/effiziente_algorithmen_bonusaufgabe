package src;
import java.util.Arrays;

public class Table {

    TableValue[][] table;
    public Table() {
    }

    public Table(TableValue[][] table) {}

    public Table(String word1, String word2, int defaultPenalty) {
        table = new TableValue[word1.length()+1][word2.length()+1];
        for (int i = 0; i < word1.length()+1; i++) {
            table[i][0] = new TableValue(i * defaultPenalty, new TableIndex[0]);
        }
        for (int i = 0; i < word2.length()+1; i++) {
            table[0][i] = new TableValue(i * defaultPenalty, new TableIndex[0]);
        }
    }

    /**
     * simple table output, created from generative ai but updated
     */
    public void printTable() {
        if (table == null || table.length == 0) {
            System.out.println("Tabelle ist leer");
            return;
        }

        // Header mit Spaltennummern
        System.out.print("     ");
        for (int col = 0; col < table[0].length; col++) {
            System.out.printf("%8d", col);
        }
        System.out.println();
        System.out.println();

        // Zeilen ausgeben
        for (int row = 0; row < table.length; row++) {
            System.out.printf("%3d: ", row);

            for (int col = 0; col < table[row].length; col++) {
                if (table[row][col] != null) {
                    String cellContent = formatCell(table[row][col]);
                    System.out.printf("%15s", cellContent);
                } else {
                    System.out.printf("%15s", "|---null---");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Formatiert eine Zelle für die Ausgabe
     */
    private String formatCell(TableValue value) {
        if (value.getPredecessors() == null || value.getPredecessors().length == 0) {
            return "|Start+V:" + value.value;
        } else {
            StringBuilder predecessors = new StringBuilder();
            for (int i = 0; i < value.getPredecessors().length; i++) {
                predecessors.append("[").append(value.getPredecessors()[i]).append("]");
            }
            return value.value + "-" + predecessors;
        }
    }
}
