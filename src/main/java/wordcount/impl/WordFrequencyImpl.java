package wordcount.impl;

import wordcount.interfaces.WordFrequency;

public class WordFrequencyImpl implements WordFrequency {

    private String word;

    private int frequency;

    public WordFrequencyImpl() {

    }
    public WordFrequencyImpl(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    @Override
    public String getWord() {
        return this.word;
    }

    @Override
    public int getFrequency() {
        return this.frequency;
    }

}
