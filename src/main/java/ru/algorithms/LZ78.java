package ru.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LZ78 {

    private String text;
    private String encodeText;
    private String decodeText;
    private List<Pair<Integer, Character>> result = new ArrayList<>();
    public LZ78(String text) {
        this.text = text;
        this.encodeText = encode(text);
        this.decodeText = decode(result);
    }
    public class Pair<T, U> {

        private final T first;
        private final U second;

        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }

        public T getFirst() {
            return first;
        }

        public U getSecond() {
            return second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Pair<?, ?> pair = (Pair<?, ?>) o;

            if (first != null ? !first.equals(pair.first) : pair.first != null) return false;
            return second != null ? second.equals(pair.second) : pair.second == null;
        }

        @Override
        public int hashCode() {
            int result = first != null ? first.hashCode() : 0;
            result = 31 * result + (second != null ? second.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "(" + first + ", " + second + ")";
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

    public String encode(String input) {
        Map<String, Integer> dictionary = new HashMap<>();
        int nextCode = 1;
        String current = "";

        for (char c : input.toCharArray()) {
            String candidate = current + c;
            if (dictionary.containsKey(candidate)) {
                current = candidate;
            } else {
                result.add(new Pair<>(dictionary.getOrDefault(current, 0), c));
                dictionary.put(candidate, nextCode++);
                current = "";
            }
        }

        if (!current.isEmpty()) {
            result.add(new Pair<>(dictionary.getOrDefault(current, 0), null));
        }

        return result.toString();
    }

    public String decode(List<Pair<Integer, Character>> input) {
        Map<Integer, String> dictionary = new HashMap<>();
        StringBuilder result = new StringBuilder();
        int nextCode = 1;

        for (Pair<Integer, Character> pair : input) {
            int code = pair.getFirst();
            char c = pair.getSecond() != null ? pair.getSecond() : '\0';
            String entry = dictionary.getOrDefault(code, "");
            entry += c;
            result.append(entry);
            dictionary.put(nextCode++, entry);
        }

        return result.toString();
    }
}