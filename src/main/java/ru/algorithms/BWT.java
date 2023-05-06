package ru.algorithms;

import java.util.*;

public class BWT {
    private String text;
    private String encodeText;
    private String decodeText;
    private double[] charFrequency;
    private int currentStr;
    public BWT(String text){
        this.text = text;
        this.encodeText = encode(text);
        this.decodeText = decode(this.encodeText);
    }
    public String encode(String input) {
        int length = input.length();
        String[] matrix = new String[length];
        // Создаем матрицу строк
        for (int i = 0; i < length; i++) {
            matrix[i] = input.substring(i) + input.substring(0, i);
        }
        // Сортируем матрицу строк
        Arrays.sort(matrix);
        // Получаем последнюю колонку матрицы
        StringBuilder result = new StringBuilder();
        for (String s : matrix) {
            result.append(s.charAt(length - 1));
        }
        currentStr = Arrays.stream(matrix).toList().indexOf(this.text);
        return result.toString();
    }
    private String decode(String input) {
        char[] bwtArray = input.toCharArray();
        int length = bwtArray.length;

        // Получение вектора обратного преобразования
        Vector<Integer> vector = new Vector<>();
        for (int i = 0; i < length; i++) {
            vector.add(i);
        }
        vector.sort((a, b) -> bwtArray[a] - bwtArray[b]);
        int[] inverse = vector.stream().mapToInt(Integer::intValue).toArray();

        // Декодирование с использованием вектора обратного преобразования
        char[] decodedArray = new char[length];
        int currentIndex = inverse[currentStr];
        for (int i = 0; i < length; i++) {
            decodedArray[i] = bwtArray[currentIndex];
            currentIndex = inverse[currentIndex];
        }

        return new String(decodedArray);
        /*int length = input.length();
        List<String> matrix = new ArrayList<>();

        for (int i = 0; i < length; i++)
        {
            matrix.add(String.valueOf(input.charAt(i)));
            //tableOfStrings[i].push_back(string[i]); tableOfStrings.get(i).add(String.valueOf(string.charAt(i)));
        }
        Collections.sort(matrix);
        for (int j = 0; j < length - 1; j++)
        {
            for (int i = 0; i < length; i++)
            {
                String currentString = matrix.get(i);
                matrix.set(i, input.charAt(i) + currentString);
            }
            Collections.sort(matrix);
        }*/ // correct
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