package wordcount.impl;

import org.springframework.stereotype.Service;
import wordcount.interfaces.WordFrequency;
import wordcount.interfaces.WordFrequencyAnalyzer;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class WordFrequencyAnalyzerImpl implements WordFrequencyAnalyzer {

    private TreeMap<String, Integer> wordsWithFrequency = new TreeMap<>();

    private List<String> totalWords;

    /**
     * @param text is the sentence where we get the highest frequent word from
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

    @Override
    public int calculateFrequencyForWord(String text, String word) {
        // The regex has the pattern that it should have at least 2 characters between a and z
        Pattern p = Pattern.compile("^[a-z]{2,}$");

        word = word.toLowerCase();

        // If the word doesn't match the regex we return 0;
        if (!p.matcher(word).find()) {
            System.out.printf("'%s' - Doesn't match regex", word);
            return 0;
        }

        int freq = 0;

        totalWords = getAllWords(text);

        // Check for every word in the text if it matches the word searched on
        for (String s : totalWords) {
            if (s.contains(word)) {
                freq++;
            }
        }

        return freq;
    }

    @Override
    public List<WordFrequency> calculateMostFrequentNWords(String text, int n) { ;

        List<WordFrequency> wordFrequencyList = new ArrayList<>();

        wordsWithFrequency = getWordsWithFrequency(text);

        Collection<Integer> maxValue = wordsWithFrequency.values();
        int highestFrequency = Collections.max(maxValue);

        for (int i = 0; i < n; i++) {
            for (Map.Entry<String, Integer> entry : wordsWithFrequency.entrySet()) {
                if (entry.getValue() >= highestFrequency) {
                    if (wordFrequencyList.size() < n) {
                        wordFrequencyList.add(new WordFrequencyImpl(entry.getKey(), entry.getValue()));
                        wordsWithFrequency.replace(entry.getKey(), entry.getValue(), -1);
                        if (!wordsWithFrequency.containsValue(highestFrequency)) {
                            highestFrequency--;
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        wordsWithFrequency.clear();

        return wordFrequencyList;
    }

    public TreeMap<String, Integer> getWordsWithFrequency(String text) {
        totalWords = getAllWords(text);

        // Put the word as key and the frequency as value in the TreeMap
        for (String s : totalWords) {
            wordsWithFrequency.put(s, calculateFrequencyForWord(text, s));
        }

        return wordsWithFrequency;
    }

    public List<String> getAllWords(String text) {
        String[] splittedWords = text.toLowerCase().split("\\s+");
        totalWords = new ArrayList<>(Arrays.asList(splittedWords));

        // Sort the list for future use
        Collections.sort(totalWords);
        return totalWords;
    }

}