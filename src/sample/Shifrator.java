package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shifrator {

    private static List<Integer> key = new ArrayList<>();
    private int k;
    private static String text;
    private static String codedText;
    private String convertedText;
    private static String language;
    private static int length;

    public Shifrator(int k, String text){
        Shifrator.text = text;
        this.k = k;
    }
    public Shifrator(List<Integer> key, int k, String codedText){
        this.key = key;
        Shifrator.codedText = codedText;
        this.k = k;
    }

    public static void setLanguage(String language) {
        Shifrator.language = language;
    }
    public static void setLength(int length) {
        Shifrator.length = length;
    }
    public static void setText(String text) {
        Shifrator.text = text;
    }
    public static void setCodedText(String codedText) {
        Shifrator.codedText = codedText;
    }
    public static void setKey(List<Integer> key) {
        Shifrator.key = key;
    }

    public static String getCodedText() {
        return codedText;
    }
    public static List<Integer> getKey() {
        return key;
    }
    public static int getLength() {
        return length;
    }

    public List<Integer> genKey() {
        Random r = new Random();
        for (int i = 0; i < k; i++) {
            int keys = 1 + r.nextInt(k);
            if (!key.contains(keys) && i != keys)
                key.add(keys);
            else i--;
        }
        return key;
    }

    public void Encipher() {
        int l = text.length();
        Random r = new Random();
        convertedText = "";
        for (int start = 0; start<l;start+=k) {

            String splitText = text.substring(start, Math.min(l, start + k));

            while (splitText.length()<k) {
                char randChar = language.charAt(r.nextInt(language.length()));
                splitText += randChar;
            }

            for (int i = 0; i < k; i++) {
                int num = key.get(i);
                char myChar = splitText.charAt(num - 1);
                char [] buffMas = {myChar};
                String buff = new String(buffMas);
                convertedText = convertedText.concat(buff);
            }
            splitText = "";
        }
    }

    public String Decode()
    {
        convertedText=codedText;

        String decodeText = "";
        int originalLength;

        int l = convertedText.length();
        for (int start = 0; start<l;start+=k)
        {
            char [] buff = new char[k];
            String splitText = convertedText.substring(start, Math.min(l, start + k));

            for (int i = 0; i < k; i++) {
                int num = key.get(i);
                char myChar = splitText.charAt(i);
                buff [num-1] = myChar;
            }
            String buffStr = new String(buff);
            decodeText = decodeText.concat(buffStr);
        }

        if (length!=0){
            originalLength = length;
        } else {
            originalLength = decodeText.length();
        }
        if (decodeText.length() != originalLength){
            text = decodeText.substring(0,originalLength);
        } else {
            text = decodeText;
        }
        return text;
    }

    public static String InEnglAlphabet(){
        String alphabet;
        alphabet = "qwerty-,uiopa.sdf ghjklz!x:c?vbnm;";
        return alphabet;
    }

    public static String InUkrAlphabet(){
        String alphabet;
        alphabet = "йцук?!еен;г-шщ:зхїфі.п,ролдж єячсм'итьбю";
        return alphabet;
    }

    public static String InRusAlphabet(){
        String alphabet;
        alphabet = "йцу;ке:нгшщзх ъфыва-пролджэ?ячсмит!,ьбю.";
        return alphabet;
    }

    public String printCodedText (){
        return convertedText;
    }

    public String printKey (){
        String keyListStr = "";
        for (int i=0;i<k;i++){
            int buffInt = key.get(i);
            String buffString = Integer.toString(buffInt);
            keyListStr+=buffString;
        }
        return keyListStr;
    }
}
