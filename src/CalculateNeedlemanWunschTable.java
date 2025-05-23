package src;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CalculateNeedlemanWunschTable {
    private static final String EVALUATION_CONFIG_PATH = "evaluation.properties";
    private static int defaultPenalty = -1;
    private static int twoVowelsPenalty = -1;
    private static int twoConsonantPenalty = -1;
    private static int consonantVowelsPenalty = -1;

    private CalculateNeedlemanWunschTable() {
        // Private constructor prevents instantiation
    }

    public static void calculateTable(String word1, String word2) {
        readEvaluationParameters();
        //initialize table as table-datastructure
        Table table = new Table(word1, word2, defaultPenalty);
        table.printTable();


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