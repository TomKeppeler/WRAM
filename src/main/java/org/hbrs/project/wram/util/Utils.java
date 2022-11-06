package org.hbrs.project.wram.util;

import java.util.Arrays;

public class Utils {
    public static boolean hasNumber(String text){
        for(char c : text.toCharArray())
            if( Character.isDigit(c) ) return true;

        return false;
    }

    public static boolean hasUpperCaseLetter(String text) {
        for (char c : text.toCharArray()) {
            // A - Z
            if (c >= 'A' && c <= 'Z')
                return true;
            // Ö, Ü, Ä
            if (c == 'Ö' || c == 'Ä' || c == 'Ü')
                return true;
        }
        return false;
    }

    public static boolean emailadresseCheck(String emailadresse) {
        boolean retValue = true;
        int i = emailadresse.indexOf("@");
        int j = emailadresse.indexOf(".", i);

        if (i == 0) { // Anzahl der Zeichen vor dem @
            retValue = false;
        }

        if (j == -1)  { // Prüft ob kein Punkt nach dem @ Zeichen kommt
            retValue = false;
        }
        if ((j - i) < 2)  { // Prüft Anzahl der Zeichen zwischen dem @ und dem .
            retValue = false;
        }
        if (j == (emailadresse.length()-1)) { // Mail Adresse muss länger sein, als die Stelle vom Punkt
            retValue = false;
        }
        return retValue;
    }

    public static boolean isAlpha(String text) {
        if(text.length()==0){return false;}
        for (char c : text.toCharArray()) {
            // a - z                    // A - Z                       // ö, ü, ä, ß
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == 'ö' || c == 'ß' || c == 'ä' || c == 'ü' )
                continue;
            return false;
        }
        return true;
    }
    public static <T> T[] append(T[] arr, T element) {
        final int N = arr.length;
        arr = Arrays.copyOf(arr, N + 1);
        arr[N] = element;
        return arr;

    }
}
