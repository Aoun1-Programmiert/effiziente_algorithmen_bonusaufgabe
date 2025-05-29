package src.Table;

/**
 * Represents the dynamic programming table used in the Needleman-Wunsch algorithm.
 *
 * Each cell contains a score (value) and a list of predecessors (used for traceback).
 * The table is sized (word1.length + 1) x (word2.length + 1) to account for gaps.
 */
public class Table {

    // 2D array of table values (cells)
    TableValue[][] table;

    // Input strings being aligned
    public String word1;
    public String word2;

    // Dimensions of the table (excluding the gap row/column)
    public int width;
    public int height;

    /**
     * Initializes the table and fills the first row and column with gap penalties.
     *
     * @param word1           the first string to align
     * @param word2           the second string to align
     * @param defaultPenalty  the penalty for introducing a gap
     */
    public Table(String word1, String word2, int defaultPenalty) {
        this.word1 = word1;
        this.word2 = word2;
        this.width = word1.length();
        this.height = word2.length();
        table = new TableValue[width + 1][height + 1];

        // Initialize first column with gap penalties
        for (int i = 0; i <= width; i++) {
            table[i][0] = new TableValue(i * defaultPenalty, new TableIndex[]{
                    new TableIndex(i - 1, 0)
            });
        }

        // Initialize first row with gap penalties
        for (int j = 0; j <= height; j++) {
            table[0][j] = new TableValue(j * defaultPenalty, new TableIndex[]{
                    new TableIndex(0, j - 1)
            });
        }
    }

    /**
     * Gets the TableValue object at the specified position.
     *
     * @param row the row index (i)
     * @param col the column index (j)
     * @return the TableValue at the specified position
     */
    public TableValue getValue(int row, int col) {
        return table[row][col];
    }

    /**
     * Sets a value and its predecessors in the table at the given position.
     *
     * @param row          the row index
     * @param col          the column index
     * @param value        the alignment score
     * @param predecessors the predecessor positions that led to this score
     */
    public void setValue(int row, int col, int value, TableIndex[] predecessors) {
        table[row][col] = new TableValue(value, predecessors);
    }

    /**
     * Returns the value of the optimal alignment in the bottom-right cell.
     *
     * @return final alignment score
     */
    public int getValueOfOptSolution() {
        return table[width][height].value;
    }

    /**
     * Prints the entire table in a human-readable format,
     * including scores and their predecessors.
     */
    public void printTable() {
        if (table == null || table.length == 0) {
            System.out.println("Tabelle ist leer");
            return;
        }

        // Print header row
        System.out.print("     ");
        System.out.printf("%-20s", " ");
        System.out.printf("%-20s", "0");
        for (int col = 0; col < table[0].length - 1; col++) {
            System.out.printf("%-20s", word2.charAt(col));
        }
        System.out.println();

        // Print each row of the table
        for (int row = 0; row < table.length; row++) {
            if (row == 0) {
                System.out.printf("%-21s", row);
            } else {
                System.out.printf("%-20s ", word1.charAt(row - 1));
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
     * Formats a cell by showing its score and its list of predecessors.
     *
     * @param value the TableValue to format
     * @return a string representing the value and its traceback pointers
     */
    private String formatCell(TableValue value) {
        if (value.getPredecessors() == null || value.getPredecessors().length == 0) {
            return value.value + "-" + "[Start]";
        } else {
            StringBuilder predecessors = new StringBuilder();
            for (TableIndex predecessor : value.getPredecessors()) {
                predecessors.append("[").append(predecessor.i()).append(",").append(predecessor.j()).append("]");
            }
            return value.value + "-" + predecessors;
        }
    }
}
