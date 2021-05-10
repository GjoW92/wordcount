package wordcount.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordFrequencyAnalyzerImplTest {

    private WordFrequencyAnalyzerImpl wordFrequencyAnalyzer;

    private String text;

    @BeforeEach
    void create() {
        text = "The sun shines over the lake";

    }
    @Test
    void calculateHighestFrequency() {
        // Setup

        // Run the test
        wordFrequencyAnalyzer.calculateHighestFrequency(text);

        // Verify the results
    }

    @Test
    void calculateFrequencyForWord() {
        // Setup

        // Run the test

        // Verify the results
    }

    @Test
    void calculateMostFrequentNWords() {
        // Setup

        // Run the test

        // Verify the results
    }

    @Test
    void getWordsWithFrequency() {
        // Setup

        // Run the test

        // Verify the results
    }

    @Test
    void getAllWords() {
    }
}