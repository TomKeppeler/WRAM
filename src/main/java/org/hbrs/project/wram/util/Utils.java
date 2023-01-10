/**
 * @outhor Salah & Fabio
 * @vision 1.0
 * @Zuletzt bearbeiret: 18.11.22 by Salah
 */
package org.hbrs.project.wram.util;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

/* Utility Klasse welche Methoden enthält, die Eigenschaften von eingegebenen Strings überprüft.*/

public class Utils {

    /**
     *  überprüfe ob ein String ein nummer hat
     * @param text
     * @return
     */
    public static boolean hasNumber(String text) {
        for (char c : text.toCharArray())
            if (Character.isDigit(c)) return true;

        return false;
    }

    /**
     * überprüfe, ob ein String ein gross Bochtabe hat
     * @param text
     * @return
     */
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

    /**
     * überprüfe, ob ein String die E-mail ein @ und . hat
     * @param emailadresse
     * @return
     */
    public static boolean emailadresseCheck(String emailadresse) {
        boolean retValue = true;
        int i = emailadresse.indexOf("@");
        int j = emailadresse.indexOf(".", i);

        if (i == 0) { // Anzahl der Zeichen vor dem @
            retValue = false;
        }

        if (j == -1) { // Prüft ob kein Punkt nach dem @ Zeichen kommt
            retValue = false;
        }
        if ((j - i) < 2) { // Prüft Anzahl der Zeichen zwischen dem @ und dem .
            retValue = false;
        }
        if (j == (emailadresse.length() - 1)) { // Mail Adresse muss länger sein, als die Stelle vom Punkt
            retValue = false;
        }
        return retValue;
    }

    /**
     * überprüfe ob ein String Bochtaben hat
     * @param text
     * @return
     */
    public static boolean isAlpha(String text) {
        if (text.length() == 0) {
            return false;
        }
        for (char c : text.toCharArray()) {
            // a - z                    // A - Z                       // ö, ü, ä, ß
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == 'ö' || c == 'ß' || c == 'ä' || c == 'ü')
                continue;
            return false;
        }
        return true;
    }

    /**
     * Array wird um ein element erweiter
     * @param arr
     * @param element
     * @return
     * @param <T>
     */
    public static <T> T[] append(T[] arr, T element) {
        final int N = arr.length;
        arr = Arrays.copyOf(arr, N + 1);
        arr[N] = element;
        return arr;

    }

    /**
     * überprüfe ob ein String ein nummer ist
     * @param text
     * @return
     */
    public static boolean isNumber(String text) {
        return StringUtils.isNumeric(text);
    }

    /**
     * überprüfe ob ein String ein telefonnummer ist
     * @param telefonnummer
     * @return
     */
    public static boolean telefonnummerCheck(String telefonnummer) {
        if (telefonnummer.length() == 0) {
            return true;
        }
        if (telefonnummer.length() <= 6 || telefonnummer.length() >= 15) return false; // Länge 9-13
        else if (telefonnummer.charAt(0) == '+' && isNumber(telefonnummer.substring(1, telefonnummer.length() - 1)))
            return true;
        else return isNumber(telefonnummer);
    }

    /**
     * @apiNote this method is used to create compute a byte array to a image
     * @param profileImage : byte[]
     * @return Image
     */
    public static Image generateImage(byte[] profileImage) {
        StreamResource sr = new StreamResource("user", () -> new ByteArrayInputStream(profileImage));
        return new Image(sr, "profile-pricture");
    }
}
