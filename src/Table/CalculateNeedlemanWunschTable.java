package src.Table;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class CalculateNeedlemanWunschTable {
    private static final String EVALUATION_CONFIG_PATH = "evaluation.properties";
    private static int defaultPenalty = -1;
    private static int twoVowelsPenalty = -1;
    private static int twoConsonantPenalty = -1;
    private static int consonantVowelsPenalty = -1;
    private static String word1 = "";
    private static String word2= "";

    private CalculateNeedlemanWunschTable() {
        // Private constructor prevents instantiation
    }

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

    private static void fillTable(Table table) {
        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                int connectionPenalty = calculatePenalty(i,j) + table.getValue(i-1, j-1).value; // OPT(i,j) = αX[n],Y[m] + OPT(n−1,m−1).
                int skipCharInW1 = defaultPenalty +  table.getValue(i-1, j).value; // OPT(i,j) = δ + OPT(i−1,j)
                int skipCharInW2 = defaultPenalty +  table.getValue(i, j-1).value; // OPT(i,j) = δ + OPT(i,j−1).
                int lowestValue = min(connectionPenalty, skipCharInW1, skipCharInW2);

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

                table.setValue(i,j,lowestValue,precessors.toArray(new TableIndex[0]));
            }
        }
    }

    private static int calculatePenalty(int i, int j) {
        char char1 = word1.charAt(i-1);
        char char2 = word2.charAt(j-1);

        if(char1 == char2) {
            return 0;
        } else {
            boolean char1Vowel = isVowel(char1);
            boolean char2Vowel = isVowel(char2);
            if(char1Vowel && char2Vowel) {
                return twoVowelsPenalty;
            } else if (!char1Vowel && !char2Vowel) {
                return twoConsonantPenalty;
            } else {
                return consonantVowelsPenalty;
            }
        }

    }
    private static int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }

    /**
     * Checks if a character is a vowel
     */
    private static boolean isVowel(char c) {
        char lowerC = Character.toLowerCase(c);
        return lowerC == 'a' || lowerC == 'e' || lowerC == 'i' || lowerC == 'o' || lowerC == 'u';
    }



    private static void readEvaluationParameters() {
        Properties evaluationProperties = new Properties();
        try (FileInputStream fis = new FileInputStream(EVALUATION_CONFIG_PATH)) {
            evaluationProperties.load(fis);
        } catch (IOException e) {
            System.err.println("Failed to load config: " + e.getMessage());
        }
        defaultPenalty = parseIntProperty(evaluationProperties, "defaultPenalty");
        twoVowelsPenalty = parseIntProperty(evaluationProperties, "twoVowelsPenalty");
        twoConsonantPenalty = parseIntProperty(evaluationProperties, "twoConsonantPenalty");
        consonantVowelsPenalty = parseIntProperty(evaluationProperties, "consonantVowelsPenalty");

    }

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