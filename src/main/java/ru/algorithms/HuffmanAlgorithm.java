package ru.algorithms;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class Node {
    Character ch;
    Integer freq;
    Node left = null, right = null;
    Node(Character ch, Integer freq)
    {
        this.ch = ch;
        this.freq = freq;
    }
    public Node(Character ch, Integer freq, Node left, Node right)
    {
        this.ch = ch;
        this.freq = freq;
        this.left = left;
        this.right = right;
    }
}
public class HuffmanAlgorithm {
    private String text;
    private String encodeText;
    private String decodeText;
    private String huffmanCode;
    public HuffmanAlgorithm(String text){
        this.text = text;
        buildHuffmanTree(text);
    }
    private void buildHuffmanTree(String data){
        if(data == null || data.length() == 0){
            return;
        }

        Map<Character, Integer> freq = new HashMap<>();
        for (char c: text.toCharArray())
        {
            //storing character and their frequency into Map by invoking the put() method
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(l -> l.freq));
        for (var entry: freq.entrySet())
        {
            //creates a leaf node and add it to the queue
            pq.add(new Node(entry.getKey(), entry.getValue()));
        }
        while (pq.size() != 1)
        {
            Node left = pq.poll();
            Node right = pq.poll();

            int sum = left.freq + right.freq;
            pq.add(new Node(null, sum, left, right));
        }
        Node root = pq.peek();
        Map<Character, String> huffmanCodeChars = new HashMap<>();
        encode(root, "", huffmanCodeChars);
        this.huffmanCode = huffmanCodeChars.toString();

        StringBuilder encodeSB= new StringBuilder();
        for (char c: text.toCharArray()) {
            encodeSB.append(huffmanCodeChars.get(c));
        }
        this.encodeText = encodeSB.toString();

        System.out.print("The decoded string is: ");
        StringBuilder sb = new StringBuilder();
        if (isLeaf(root))
        {
            while (root.freq-- > 0) {
                System.out.print(root.ch);
            }
        }
        else {

            int index = -1;
            while (index < sb.length() - 1) {
                index = decode(root, index, sb);
            }
        }
    }
    private boolean isLeaf(Node root) {
        return root.left == null && root.right == null;
    }
    private void encode(Node root, String str,
                              Map<Character, String> huffmanCode)
    {
        if (root == null) {
            return;
        }
        if (isLeaf(root)) {
            huffmanCode.put(root.ch, str.length() > 0 ? str : "1");
        }

        encode(root.left, str + '0', huffmanCode);
        encode(root.right, str + '1', huffmanCode);
    }
    private int decode(Node root, int index, StringBuilder sb)
    {
        if (root == null) {
            return index;
        }
        if (isLeaf(root))
        {
            System.out.print(root.ch);
            return index;
        }
        index++;

        root = (sb.charAt(index) == '0') ? root.left : root.right;
        index = decode(root, index, sb);
        return index;
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
