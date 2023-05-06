import ru.algorithms.AC;

public class Main {
    public static String text = "Hello World!!!";
    public static void main(String[] args) {
        AC ac =new AC(text);
        System.out.println("Encode: " + ac.getEncodeText());
        System.out.println("Decode: " + ac.getDecodeText());
    }
}