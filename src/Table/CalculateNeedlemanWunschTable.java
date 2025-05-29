package src.Table;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Utility class that calculates the dynamic programming table
 * for the Needleman-Wunsch global sequence alignment algorithm.
 */
public class CalculateNeedlemanWunschTable {
    private static final String EVALUATION_CONFIG_PATH = "evaluation.properties";

    // Penalty parameters read from configuration file
    private static int defaultPenalty = -1;
    private static int twoVowelsPenalty = -1;
    private static int twoConsonantPenalty = -1;
    private static int consonantVowelsPenalty = -1;

    private static String word1 = "";
    private static String word2= "";

    private CalculateNeedlemanWunschTable() {
        // Private constructor prevents instantiation
    }

    /**
     * Computes the Needleman-Wunsch table for two given words.
     *
     * @param w1 the first word (string to align)
     * @param w2 the second word (string to align)
     * @return a fully filled Table object representing the dynamic programming matrix
     */
    public static Table calculateTable(String w1, String w2) {
        //initialize
        word1 = w1;
        word2 = w2;
        readEvaluationParameters();

        //initialize table as table-datastructure
        Table table = new Table(word1, word2, defaultPenalty);
        fillTable(table);

        return table;

    }

    /**
     * Fills the dynamic programming table with alignment scores and backtrace information.
     *
     * Implements the standard Needleman-Wunsch recurrence relations:
     * - match/mismatch: OPT(i,j) = α + OPT(i−1,j−1)
     * - gap in word1:   OPT(i,j) = δ + OPT(i−1,j)
     * - gap in word2:   OPT(i,j) = δ + OPT(i,j−1)
     */
    private static void fillTable(Table table) {
        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                // Compute the score for each possible move
                int connectionPenalty = calculatePenalty(i,j) + table.getValue(i-1, j-1).value; // OPT(i,j) = αX[n],Y[m] + OPT(n−1,m−1).
                int skipCharInW1 = defaultPenalty +  table.getValue(i-1, j).value; // OPT(i,j) = δ + OPT(i−1,j)
                int skipCharInW2 = defaultPenalty +  table.getValue(i, j-1).value; // OPT(i,j) = δ + OPT(i,j−1).

                // Determine the minimum score (optimal substructure)
                int lowestValue = min(connectionPenalty, skipCharInW1, skipCharInW2);

                // Track all possible predecessors (for traceback)
                ArrayList<TableIndex> precessors = new ArrayList<>();
                if(connectionPenalty == lowestValue) {
                    precessors.add(new TableIndex(i-1, j-1));
                }
                if(skipCharInW1 == lowestValue) {
                    precessors.add(new TableIndex(i-1, j));
                }
                if(skipCharInW2 == lowestValue) {
                    precessors.add(new TableIndex(i, j-1));
                }

                // Update the current cell with the score and backtrace pointers
                table.setValue(i,j,lowestValue,precessors.toArray(new TableIndex[0]));
            }
        }
    }

    /**
     * Calculates the penalty between two characters at positions i and j.
     *
     * @param i the index in word1 (1-based)
     * @param j the index in word2 (1-based)
     * @return the substitution penalty between the two characters
     */
    private static int calculatePenalty(int i, int j) {
        char char1 = word1.charAt(i-1);
        char char2 = word2.charAt(j-1);

        if(Character.toLowerCase(char1) == Character.toLowerCase(char2)) {
            return 0; // match
        } else {
            boolean char1Vowel = isVowel(char1);
            boolean char2Vowel = isVowel(char2);

            // Assign penalties based on character type
            if(char1Vowel && char2Vowel) {
                return twoVowelsPenalty;
            } else if (!char1Vowel && !char2Vowel) {
                return twoConsonantPenalty;
            } else {
                return consonantVowelsPenalty;
            }
        }

    }

    /**
     * Utility method to find the minimum of three integers.
     */
    private static int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    /**
     * Checks if a given character is a vowel (case-insensitive).
     *
     * @param c the character to check
     * @return true if the character is a vowel, false otherwise
     */
    private static boolean isVowel(char c) {
        char lowerC = Character.toLowerCase(c);
        return lowerC == 'a' || lowerC == 'e' || lowerC == 'i' || lowerC == 'o' || lowerC == 'u';
    }


    /**
     * Reads evaluation parameters from the external configuration file.
     * These values determine the penalties used during alignment.
     */
    private static void readEvaluationParameters() {
        Properties evaluationProperties = new Properties();
        try (FileInputStream fis = new FileInputStream(EVALUATION_CONFIG_PATH)) {
            evaluationProperties.load(fis);
        } catch (IOException e) {
            System.err.println("Failed to load config: " + e.getMessage());
        }

        // Parse the penalty values from the properties file
        defaultPenalty = parseIntProperty(evaluationProperties, "defaultPenalty");
        twoVowelsPenalty = parseIntProperty(evaluationProperties, "twoVowelsPenalty");
        twoConsonantPenalty = parseIntProperty(evaluationProperties, "twoConsonantPenalty");
        consonantVowelsPenalty = parseIntProperty(evaluationProperties, "consonantVowelsPenalty");

    }

    /**
     * Utility method to safely parse an integer property from the config file.
     *
     * @param props the Properties object loaded from file
     * @param key the key to look up
     * @return the parsed integer value
     * @throws IllegalArgumentException if the key is missing or the value is not a valid integer
     */
    private static int parseIntProperty(Properties props, String key) {
        String value = props.getProperty(key);
        if (value == null)
            throw new IllegalArgumentException("Missing config value for: " + key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid integer value for: " + key + " = " + value);
        }
    }
}