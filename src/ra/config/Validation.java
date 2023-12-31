package ra.config;

import java.util.regex.Pattern;

public class Validation {
    public static boolean validateUserName(String username) {
        String regex = "^[a-zA-Z0-9._-]{3,13}$";
        return Pattern.matches(regex, username);
    }

    public static boolean validatePassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,13}$";
        return Pattern.matches(regex, password);
    }

    public static boolean validateEmail(String email) {
        String regex = "^[A-Za-z0-9]+[A-Za-z0-9._%+-]*@[a-z]+(\\.[a-z]+)$";
        return Pattern.matches(regex, email);
    }

    public static boolean validateTel(String tel) {
        String regex = "^0(\\d){9}$";
        return Pattern.matches(regex, tel);
    }

    public static boolean validateSpaces(String str) {
        String regex = "\\S+";
        return Pattern.matches(regex, str);
    }

    public static String formatPrice(Double price) {
        if (price % 1 == 0) {
            return String.format(String.valueOf(price)).replaceAll("\\.0+$", "") + ".000₫";
        }
        return price + "00₫";
    }

}
