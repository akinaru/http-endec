package fr.bmartel.protocol.http.utils;

/**
 * Some functions to deal with string
 *
 * @author Bertrand Martel
 */
public class StringUtils {

    /**
     * from
     * http://stackoverflow.com/questions/237159/whats-the-best-way-to-check
     * -to-see-if-a-string-represents-an-integer-in-java
     * <p/>
     * Efficient way to test if string is a valid integer
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c <= '/' || c >= ':') {
                return false;
            }
        }
        return true;
    }
}
