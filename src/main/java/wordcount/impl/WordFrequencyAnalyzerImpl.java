package wordcount.impl;

import org.springframework.stereotype.Service;
import wordcount.interfaces.WordFrequency;
import wordcount.interfaces.WordFrequencyAnalyzer;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class WordFrequencyAnalyzerImpl implements WordFrequencyAnalyzer {

    private TreeMap<String, Integer> wordsWithFrequency = new TreeMap<>();

    private String[] totalWords;

    /**
     * @param text the sentence where we get the highest frequent word from
     * @return highest frequent number
     */
    @Override
    public int calculateHighestFrequency(String text) {

        // I use TreeMap because it doesn't allow duplicate keys
        wordsWithFrequency = getWordsWithFrequency(text);

        // Get all values from the TreeMap
        Collection<Integer> maxValue = wordsWithFrequency.values();

        // Get the highest frequency that is stored in maxValue
        return Collections.max(maxValue);
    }

    /**
     * @param text the sentence where we get the highest frequent word from
     * @param word the word we use to search the frequency in the text
     * @return the frequency for the searched word
     */
    @Override
    public int calculateFrequencyForWord(String text, String word) {
        // The regex has the pattern that it should have at least 2 characters between a and z
        Pattern p = Pattern.compile("^[a-z]{2,}$");

        // We are working case insensitive
        word = word.toLowerCase();

        // If the word doesn't match the regex we return 0;
        if (!p.matcher(word).find()) {
            return 0;
        }

        int frequency = 0;

        totalWords = getAllWords(text);

        // Check for every word in the text if it matches the word searched on
        for (String s : totalWords) {
            if (s.contains(word)) {
                frequency++;
            }
        }

        return frequency;
    }

    /**
     * @param text the sentence where we get the highest frequent word from
     * @param n    the amount of words we want to get Ex: n = 3 => we get 3 words back
     * @return n amount of words that are most frequent in the sentence
     */
    @Override
    public List<WordFrequency> calculateMostFrequentNWords(String text, int n) {
        List<WordFrequency> wordFrequencyList = new ArrayList<>();

        wordsWithFrequency = getWordsWithFrequency(text);

        Collection<Integer> maxValue = wordsWithFrequency.values();
        int highestFrequency = Collections.max(maxValue);

        for (int i = 0; i < n; i++) {
            // Loop through every map entry in the TreeMap
            for (Map.Entry<String, Integer> entry : wordsWithFrequency.entrySet()) {
                // If the highest frequent number reaches 0 we've been through all words
                if (highestFrequency < 1) {
                    break;
                }
                // Check if the entry value equals the highest frequent number
                if (entry.getValue() == highestFrequency) {
                    if (wordFrequencyList.size() < n) {
                        wordFrequencyList.add(new WordFrequencyImpl(entry.getKey(), entry.getValue()));
                        // Put the entry key and value to -1, this means the key has already been added
                        wordsWithFrequency.replace(entry.getKey(), entry.getValue(), -1);
                    }

                }
                // Check if there are more entries with the highest frequent number
                // If not then lower the highest frequent number by 1
                if (!wordsWithFrequency.containsValue(highestFrequency)) {
                    highestFrequency--;
                }
            }
        }

        wordsWithFrequency.clear();

        return wordFrequencyList;
    }

    /**
     * @param text the sentence where we get the highest frequent word from
     * @return TreeMap where the words are keys and the frequency the value
     */
    public TreeMap<String, Integer> getWordsWithFrequency(String text) {
        totalWords = getAllWords(text);

        // Put the word as key and the frequency as value in the TreeMap
        for (String s : totalWords) {
            wordsWithFrequency.put(s, calculateFrequencyForWord(text, s));
        }

        return wordsWithFrequency;
    }

    /**
     * @param text the sentence where we get the highest frequent word from
     * @return all the words within the sentence and sort them alphabetically
     */
    private String[] getAllWords(String text) {
        String[] splitWords = text.toLowerCase().split("[^a-zA-Z]");
        Arrays.sort(splitWords);
        return splitWords;
    }

}