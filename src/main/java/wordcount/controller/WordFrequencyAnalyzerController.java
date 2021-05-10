package wordcount.controller;

import org.springframework.web.bind.annotation.*;
import wordcount.impl.WordFrequencyAnalyzerImpl;
import wordcount.impl.WordFrequencyImpl;
import wordcount.interfaces.WordFrequency;

import java.util.*;

@RestController
@RequestMapping("/api")
public class WordFrequencyAnalyzerController {

    private final WordFrequencyAnalyzerImpl wordFrequencyAnalyzer;

    private int frequency;

    public WordFrequencyAnalyzerController(WordFrequencyAnalyzerImpl wordFrequencyAnalyzer) {
        this.wordFrequencyAnalyzer = wordFrequencyAnalyzer;
    }

    @GetMapping("/frequency")
    @ResponseBody
    public WordFrequency calculateHighestFrequency(@RequestParam String text) {
        if(text.equals("") || text.length() < 2) {
            System.out.println("Invalid text to calculate with");
            return null;
        }

        frequency = wordFrequencyAnalyzer.calculateHighestFrequency(text);

        TreeMap<String, Integer> wordsWithFrequency = wordFrequencyAnalyzer.getWordsWithFrequency(text);

        // Get all the keys where the value equals the highest frequency
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordsWithFrequency.entrySet()) {
            if (entry.getValue().equals(frequency)) {
                keys.add(entry.getKey());
            }
        }

        return new WordFrequencyImpl(keys.toString(),frequency);
    }

    @GetMapping("/word")
    @ResponseBody
    public WordFrequency calculateFrequencyForWord(@RequestParam String text, @RequestParam String word) {
        frequency = wordFrequencyAnalyzer.calculateFrequencyForWord(text, word);

        return new WordFrequencyImpl(word, frequency);
    }

    @GetMapping("/frequencies/{n}")
    @ResponseBody
    public List<WordFrequency> calculateMostFrequentNWords(@RequestParam String text, @PathVariable int n) {

        return wordFrequencyAnalyzer.calculateMostFrequentNWords(text, n);
    }
}
