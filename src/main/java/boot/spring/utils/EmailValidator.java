package boot.spring.utils; // Defines the package name as boot.spring.utils.

import java.util.regex.Matcher; // Imports the Matcher class from the java.util.regex package for performing match operations on text.
import java.util.regex.Pattern; // Imports the Pattern class from the java.util.regex package for defining patterns for search operations.

// Declares the public class EmailValidator for validating email addresses.
public class EmailValidator {
    // Declares a static final Pattern named EMAIL_PATTERN.
    // This pattern is compiled from the given regex which matches a standard email format.
    // The regex ensures that the email starts with alphanumeric characters (including ., _, %, +, and -),
    // followed by an @ symbol, then more alphanumeric characters (including . and -),
    // and ends with a dot followed by two or more alphabetic characters.
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // Declares a public static method isValidEmail that takes a String email as input and returns a boolean.
    // This method checks if the given email string matches the EMAIL_PATTERN.
    public static boolean isValidEmail(String email) {
        if (email == null) { // Checks if the input email is null.
            return false; // Returns false if email is null, meaning it's not valid.
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email); // Creates a Matcher object for the input email using EMAIL_PATTERN.
        return matcher.matches(); // Returns true if the email matches the pattern, false otherwise.
    }
}
