package ru.algorithms;

import java.util.ArrayList;
import java.util.List;

public class MTF {
    private String text;
    private String encodeText;
    private String decodeText;
    private static final int ALPHABET_SIZE = 256;
    List<Character> alphabet = new ArrayList<>();
    public MTF(String text) {
        this.text = text;
        for(int i= 0;i<ALPHABET_SIZE;i++){
            alphabet.add((char) i);
        }
        List<Integer> encodedList = encode(text);
        this.encodeText = encodedList.toString();
        this.decodeText = decode(encodedList);
    }
    private List<Integer> encode(String input){
        List<Integer> result = new ArrayList<>();
        for(int i = 0;i<input.length();i++){
            int finalI = i;
            char temp = alphabet.stream()
                    .filter(p->p==input.charAt(finalI))
                    .findFirst()
                    .orElse('\0');
            if (temp != '\0') {
                result.add((int) temp);
                alphabet.remove((Character) temp);
                alphabet.add(0,temp);
            }
        }
        return result;
    }

    private String decode(List<Integer> input){
        StringBuilder result = new StringBuilder();
        for(int i = 0;i<input.size();i++){
            int finalI = i;
            int temp = alphabet.stream()
                    .filter(p->(int)p==input.get(finalI))
                    .findFirst()
                    .orElse('\0');
            if (temp != -1) {
                result.append((char)temp);
                alphabet.remove((Character)(char)temp);
                alphabet.add(0,(char)temp);
            }
        }
        return result.toString();
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