package ru.algorithms;

import java.util.*;

public class AC {
    private String text;
    private String encodeText;
    private String decodeText;
    private int lengthText;
    private char[] alphabet;
    private double[] symbolProbabilities;
    public AC(String text) {
        this.text = text;
        this.lengthText = text.length();
        this.alphabet = defineAlphabet(text);
        this.symbolProbabilities = defineSymbolProbabilities(text);

        this.encodeText = String.valueOf(encode(text));
        this.decodeText = decode(encodeText);
    }
    private char[] defineAlphabet(String input){
        Set<Character> alphabetSet = new HashSet<>();
        for (int i = 0; i < input.length(); i++) {
            alphabetSet.add(input.charAt(i));
        }
        char[] alphabet = new char[alphabetSet.size()];
        int i = 0;
        for (Character c : alphabetSet) {
            alphabet[i++] = c;
        }
        Arrays.sort(alphabet);
        return alphabet;
    }
    private double[] defineSymbolProbabilities(String input){
        Map<Character, Integer> charFrequency = new HashMap<>();
        double[] symbolProbabilities = new double[256];
        int totalChars = input.length();
        for(int i = 0;i<totalChars;i++){
            char c = input.charAt(i);
            if(charFrequency.containsKey(c)){
                charFrequency.put(c,charFrequency.get(c)+1);
                continue;
            }
            charFrequency.put(c,1);
        }
        /*// Calculate the frequency of each character in the input string
        for (int i = 0; i < totalChars; i++) {
            char c = input.charAt(i);
            charFrequency.put(c, charFrequency.getOrDefault(c, 0) + 1);
        }*/

        // Calculate the probability of each character
        for (Map.Entry<Character, Integer> entry : charFrequency.entrySet()) {
            char c = entry.getKey();
            int freq = entry.getValue();
            symbolProbabilities[c] = (double) freq / totalChars;
        }

        return symbolProbabilities;
    }
    private Range[] defineRangeEncode(){
        Range[] ranges = new Range[256];
        double left = 0;
        for(int i = 0;i<alphabet.length;i++){
            ranges[(int)alphabet[i]] = new Range();
            ranges[(int)(alphabet[i])].setLeft(left);
            ranges[(int)(alphabet[i])].setRight(left+symbolProbabilities[(int)(alphabet[i])]);
            left = ranges[(int)(alphabet[i])].getRight();
        }
        return ranges;
    }
    private double encode(String input){
        Range[] ranges = defineRangeEncode();
        double low = 0;
        double high = 1;
        for (int i = 0; i < lengthText; i++) {
            char c = text.charAt(i);
            double rangeSize = high - low;
            high = low + rangeSize * ranges[c].getRight();
            low = low + rangeSize * ranges[c].getLeft();
        }
        return (low + high) / 2;
    }
    private Range[] defineRangeDecode() {
        Range[] ranges = new Range[alphabet.length];
        double left = 0;
        for(int i = 0;i<alphabet.length;i++){
            ranges[i] = new Range();
            ranges[i].setLeft(left);
            ranges[i].setRight(left+symbolProbabilities[(int)(alphabet[i])]);
            ranges[i].setWord(alphabet[i]);
            left = ranges[i].getRight();
        }
        return ranges;
    }
    private String decode(String input){
        String result = "";
        double code = Double.parseDouble(encodeText);
        Range[] ranges = defineRangeDecode();
        for(int i = 0;i<lengthText;i++){
            for(int j = 0;j<alphabet.length;j++){
                if(code >=ranges[j].getLeft() && code < ranges[j].getRight()){
                    result+=ranges[j].getWord();
                    code = (code-ranges[j].getLeft())/(ranges[j].getRight()-ranges[j].getLeft());
                    break;
                }
            }
        }
        return result;
    }

    private static class Range {
        private double left;
        private double right;
        private char word;
        public Range() {}
        public double getLeft() {
            return left;
        }

        public void setLeft(double left) {
            this.left = left;
        }

        public double getRight() {
            return right;
        }

        public void setRight(double right) {
            this.right = right;
        }

        public char getWord() {
            return word;
        }

        public void setWord(char word) {
            this.word = word;
        }
    }
    public String getText() {
        return text;
    }

    public String getEncodeText() {
        return encodeText;
    }

    public String getDecodeText() {
        return decodeText;
    }
}