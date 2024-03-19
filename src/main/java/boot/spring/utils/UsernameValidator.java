package boot.spring.utils; // Defines the package name as boot.spring.utils, indicating it's a utility class within the boot.spring project.

import java.util.regex.Matcher; // Imports the Matcher class from the java.util.regex package, used for matching character sequences against a pattern.
import java.util.regex.Pattern; // Imports the Pattern class from the java.util.regex package, used for compiling a regular expression into a pattern.

// Declares the public class UsernameValidator, intended for validating username strings against predefined criteria.
public class UsernameValidator {
    // Declares a private static final Pattern named USERNAME_PATTERN.
    // The compiled Pattern object is from a regular expression that matches usernames consisting only of
    // uppercase and lowercase letters (A-Za-z) and underscores (_).
    // The "^" signifies the start of the string, and "$" signifies the end of the string,
    // ensuring that the entire string matches the pattern.
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[A-Za-z_]+$");

    // Declares a public static method named isValidUsername, which takes a String named username as input
    // and returns a boolean indicating whether the username matches the defined pattern.
    public static boolean isValidUsername(String username) {
        if (username == null) { // Checks if the input username is null.
            return false; // Returns false, indicating that a null username is not valid.
        }
        Matcher matcher = USERNAME_PATTERN.matcher(username); // Creates a Matcher for the input username using USERNAME_PATTERN.
        return matcher.matches(); // Returns true if the entire username matches the pattern, false otherwise.
    }
}
