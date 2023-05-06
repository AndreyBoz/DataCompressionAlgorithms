package ru.algorithms;

public class RLE {
    private String text;
    private String encodeText;
    private String decodeText;
    public RLE(String text){
        this.text = text;
        this.encodeText = encodeData(text);
        this.decodeText = decodeData();
    }
    private String encodeData(String data){
        if(data == null || data.length() == 0) {
            return null;
        }
        String encoding = "";
        int count;

        for (int i = 0; i < data.length(); i++)
        {
            count = 1;
            while (i + 1 < data.length() && data.charAt(i) == data.charAt(i + 1))
            {
                count++;
                i++;
            }

            encoding += String.valueOf(count) + data.charAt(i);
        }

        return encoding;
    }
    private String decodeData(){
        if(encodeText == null || encodeText.length() == 0) {
            return null;
        }
        String decode = "";
        int count = 0;
        for(int i = 0;i<encodeText.length();i++){
            count = 0;
            if(Character.isDigit(encodeText.toCharArray()[i])) {
                while (Character.isDigit(encodeText.toCharArray()[i])) {
                    count++;
                    i++;
                }
                decode+=String.valueOf(encodeText.toCharArray()[i]).repeat(count);
            }
        }
        return decode;
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
