package src.Table;

/**
 * Class for Table.
 * First index i corresponds to the first word.
 * Second index j corresponds to the second word
 */
public class Table {

    TableValue[][] table;
    public String word1;
    public String word2;
    public int width;
    public int height;

    public Table(String word1, String word2, int defaultPenalty) {
        this.word1 = word1;
        this.word2 = word2;
        table = new TableValue[word1.length()+1][word2.length()+1];
        width = word1.length();
        height = word2.length();
        for (int i = 0; i < word1.length()+1; i++) {
            table[i][0] = new TableValue(i * defaultPenalty, new TableIndex[]{
                    new TableIndex(i-1,0)
            });
        }
        for (int j = 0; j < word2.length()+1; j++) {
            table[0][j] = new TableValue(j * defaultPenalty, new TableIndex[]{
                    new TableIndex(0,j-1)
            });
        }
    }

    public TableValue getValue(int row, int col) {
        return table[row][col];
    }

    public void setValue(int row, int col, int value, TableIndex[] predecessors) {
        table[row][col] = new TableValue(value, predecessors) ;
    }

    public int getValueOfOptSolution(){
        return table[width][height].value;
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
        System.out.printf("%-20s", " ");
        System.out.printf("%-20s", "0");
        for (int col = 0; col < table[0].length - 1; col++) {
            System.out.printf("%-20s", word2.charAt(col));
        }
        System.out.println();

        // Zeilen ausgeben
        for (int row = 0; row < table.length; row++) {
            if(row == 0) {
                System.out.printf("%-21s", row);
            } else {
                System.out.printf("%-20s ", word1.charAt(row-1));
            }


            for (int col = 0; col < table[row].length; col++) {
                if (table[row][col] != null) {
                    String cellContent = formatCell(table[row][col]);
                    System.out.printf("%-20s", cellContent);
                } else {
                    System.out.printf("%-20s", "[null]");
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
            return value.value + "-" + "[Start]";
        } else {
            StringBuilder predecessors = new StringBuilder();
            for (int k = 0; k < value.getPredecessors().length; k++) {
                predecessors.append("[").append(value.getPredecessors()[k].i()).append(",").append(value.getPredecessors()[k].j()).append("]");
            }
            return value.value + "-" + predecessors;
        }
    }
}
