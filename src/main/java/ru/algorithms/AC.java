package ru.algorithms;

import java.util.HashMap;
import java.util.Map;

public class AC {

    private Map<Character, Integer> charFrequency;
    private Map<Character, Range> charRange;

    public AC(String inputString) {
        this.charFrequency = getCharFrequency(inputString);
        this.charRange = getCharRange(charFrequency, inputString.length());
    }

    public double encode() {
        double lowerBound = 0.0;
        double upperBound = 1.0;

        for (Map.Entry<Character, Range> entry : charRange.entrySet()) {
            char c = entry.getKey();
            Range range = entry.getValue();

            double rangeSize = upperBound - lowerBound;
            upperBound = lowerBound + rangeSize * range.upper;
            lowerBound = lowerBound + rangeSize * range.lower;
        }

        double code = (upperBound + lowerBound) / 2.0;
        return code;
    }
    public String decode(double code, int length) {
        StringBuilder decodedString = new StringBuilder(length);
        double lowerBound = 0.0;
        double upperBound = 1.0;
        int alphabetSize = 26; // assume all characters are upper case letters

        for (int i = 0; i < length; i++) {
            double rangeSize = upperBound - lowerBound;
            for (int j = 0; j < alphabetSize; j++) {
                char c = (char) ('A' + j); // Assume all characters are upper case letters
                Range range = charRange.get(c);
                if (range == null) continue; // skip non-existent characters

                double symbolLowerBound = lowerBound + rangeSize * range.lower;
                double symbolUpperBound = lowerBound + rangeSize * range.upper;

                if (code >= symbolLowerBound && code < symbolUpperBound) {
                    decodedString.append(c);
                    upperBound = symbolUpperBound;
                    lowerBound = symbolLowerBound;
                    break;
                }
            }
        }

        return decodedString.toString();
    }
    private Map<Character, Integer> getCharFrequency(String inputString) {
        Map<Character, Integer> charFrequency = new HashMap<>();
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);
            charFrequency.put(c, charFrequency.getOrDefault(c, 0) + 1);
        }
        return charFrequency;
    }

    private Map<Character, Range> getCharRange(Map<Character, Integer> charFrequency, int totalChars) {
        Map<Character, Range> charRange = new HashMap<>();
        double lower = 0.0;
        for (Map.Entry<Character, Integer> entry : charFrequency.entrySet()) {
            char c = entry.getKey();
            int freq = entry.getValue();
            double upper = lower + ((double) freq / totalChars);
            charRange.put(c, new Range(lower, upper));
            lower = upper;
        }
        return charRange;
    }

    private static class Range {
        double lower;
        double upper;

        Range(double lower, double upper) {
            this.lower = lower;
            this.upper = upper;
        }

        @Override
        public String toString() {
            return "[" + lower + ", " + upper + "]";
        }
    }
}
