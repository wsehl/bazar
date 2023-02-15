package cli;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Menu {
    
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    public static final Pattern PASSWORD_STRENGTH_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[\\S]{8,10}$");

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validatePassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        return true;
    }

    public static boolean getPasswordStrength(String password) {
        Matcher matcher = PASSWORD_STRENGTH_REGEX.matcher(password);
        return matcher.find();
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String getStrengthBar(int percent) {
        char barCompleteChar = '\u2588';
        char barIncompleteChar = '\u2591';

        String bar = "";

        for (int i = 0; i < 10; i++) {
            if (i < percent / 10) {
                bar += barCompleteChar;
            } else {
                bar += barIncompleteChar;
            }
        }

        return bar;
    }

    public void outputLogo() {
        System.out.println(
              "\n████████████████████████████████\n" +
                "██░░░░░░░░░░░░███░░░░░░░░░░░░░██\n" +
                "██░░░░████░░░░██████████░░░░████\n" +
                "██░░░░░░░░███████████░░░░███████\n" +
                "██░░░░████░░░░█████░░░░█████████\n" +
                "██░░░░░░░░░░░░███░░░░░░░░░░░░░██\n" +
                "████████████████████████████████");
    }

}
