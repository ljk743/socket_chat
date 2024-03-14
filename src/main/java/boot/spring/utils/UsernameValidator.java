package boot.spring.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator {
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z_]+$");

    public static boolean isValidUsername(String username) {
        if (username == null) {
            return false;
        }
        Matcher matcher = USERNAME_PATTERN.matcher(username);
        return matcher.matches();
    }
}

