package wordcount.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import wordcount.impl.WordFrequencyAnalyzerImpl;
import wordcount.impl.WordFrequencyImpl;
import wordcount.interfaces.WordFrequency;

import java.util.*;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class WordFrequencyController {

    private final WordFrequencyAnalyzerImpl wordFrequencyAnalyzer;

    private int frequency;

    public WordFrequencyController(WordFrequencyAnalyzerImpl wordFrequencyAnalyzer) {
        this.wordFrequencyAnalyzer = wordFrequencyAnalyzer;
    }

    @GetMapping("/frequency")
    @ResponseBody
    public WordFrequency calculateHighestFrequency(@RequestParam String text) {
        Pattern p = Pattern.compile("^\\D+\\s.+");
        if(text == null || !p.matcher(text).find()) {
            System.out.println("INVALID TEXT");
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

        return new WordFrequencyImpl(word.toLowerCase(), frequency);
    }

    @GetMapping("/frequencies/{n}")
    @ResponseBody
    public List<WordFrequency> calculateMostFrequentNWords(@RequestParam String text, @PathVariable int n) {

        return wordFrequencyAnalyzer.calculateMostFrequentNWords(text, n);
    }
}
