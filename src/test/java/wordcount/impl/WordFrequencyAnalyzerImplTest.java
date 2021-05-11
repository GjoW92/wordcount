package wordcount.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import wordcount.WordcountApplication;
import wordcount.interfaces.WordFrequency;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = WordcountApplication.class)
class WordFrequencyAnalyzerImplTest {
    @Autowired
    private WordFrequencyAnalyzerImpl wordFrequencyAnalyzer;

    private String text;

    private int frequency;

    private String[] words;

    List<String> lowerCaseWordList;

    @BeforeEach
    void setUp() {
        wordFrequencyAnalyzer = new WordFrequencyAnalyzerImpl();
        text = "The sun shines over the lake";
        words = text.split("[^a-zA-Z]");
        lowerCaseWordList = new ArrayList<>();
        for(String s : words) {
            lowerCaseWordList.add(s.toLowerCase());
        }
        frequency = 0;
    }

    /**
     * Calculating the highest frequent number
     * Loop through list containing words and check if list.get(i) equals the word
     * Increment the placeholder number and set int frequent to placeholder
     * If we find a bigger placeholder number I overwrite the frequent integer
     */
    @Test
    void calculateHighestFrequency() {
        // Run the test
        for (int i = 0; i < lowerCaseWordList.size(); i++) {
            int placeholder = 0;
            for (String s : lowerCaseWordList) {
                if (lowerCaseWordList.get(i).equals(s)) {
                    placeholder++;
                }
            }
            if (placeholder >= frequency) {
                frequency = placeholder;
            }
        }

        int result = wordFrequencyAnalyzer.calculateHighestFrequency(text);

        // Verify the results
        assertEquals(frequency, result);
    }

    /**
     * Creating a list of words
     * Loop through list of words and find if s equals word
     * Increment the frequency if true
     */
    @Test
    void calculateFrequencyForWord() {
        String word = "the";

        // Run the test
        for (String s : lowerCaseWordList) {
            if (s.equals(word)) {
                frequency++;
            }
        }

        int result = wordFrequencyAnalyzer.calculateFrequencyForWord(text, word);

        // Verify the results;
        assertEquals(frequency, result);
    }

    /**
     * This returns a list of words
     * The list should not be bigger than n
     */
    @Test
    void calculateMostFrequentNWords() {
        // Setup
        int n = 3;

        // Run the test
        List<WordFrequency> result = wordFrequencyAnalyzer.calculateMostFrequentNWords(text, n);

        // Verify the results
        assertFalse(result.size() > n);
    }

    /**
     * Map words with their frequency (the, 2)
     * The method should return a TreeMap with the same results as the test code
     */
    @Test
    void getWordsWithFrequency() {
        // Setup
        TreeMap<String, Integer> treeMap = new TreeMap<>();

        // Run the test
        for (int i = 0; i < lowerCaseWordList.size(); i++) {
            int placeholder = 0;
            for (String s : lowerCaseWordList) {
                if (lowerCaseWordList.get(i).equals(s)) {
                    placeholder++;
                    treeMap.put(s,placeholder);
                }
            }
        }

        TreeMap<String, Integer> result = wordFrequencyAnalyzer.getWordsWithFrequency(text);

        // Verify the results
        assertEquals(treeMap, result);
    }

    /**
     * Splitting up the text and recreating the sentence
     * If all went correctly, text should equal sentence
     * We use trim to remove leading and trailing whitespaces
     */
    @Test
    void getAllWords() {
        // Setup
        StringBuilder sentence = new StringBuilder();

        // Run the test
        for (String s : words) {
            sentence.append(s).append(" ");
        }
        String result = sentence.toString().trim();
        // Verify the results
        assertEquals(text.trim(), result);
    }
}