package boot.spring.utils; // Defines the package name as boot.spring.utils.

import java.util.regex.Pattern; // Imports the Pattern class from the java.util.regex package for compiling regex patterns.

// Declares the public class PasswordValidator for validating passwords based on specified criteria.
public class PasswordValidator {

    // Declares a private static final String named PASSWORD_PATTERN.
    // This string contains a regular expression that defines the password validation rules:
    // - At least one uppercase letter (?=.*[A-Z])
    // - At least one lowercase letter (?=.*[a-z])
    // - At least one special character among (!@#%*&) (?=.*[!@#%*&])
    // - At least one digit (?=.*[0-9])
    // - A minimum length of 8 characters
    // The overall structure [A-Za-z!@#%*&0-9]{8,}$ ensures the password consists of allowed characters
    // and meets the minimum length requirement.
    private static final String PASSWORD_PATTERN =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#%*&])(?=.*[0-9])[A-Za-z!@#%*&0-9]{8,}$";

    // Compiles the PASSWORD_PATTERN into a Pattern instance for efficient reuse.
    // This compiled Pattern object will be used to match strings against the regular expression.
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    // Declares a public static method named isValid. This method takes a String password as input
    // and returns a boolean indicating whether the password matches the defined pattern.
    public static boolean isValid(final String password) {
        if (password == null) { // Checks if the input password is null.
            return false; // Returns false if password is null, indicating it's not valid.
        }
        // Uses the pattern to create a matcher for the input password.
        // The matches() method checks whether the entire password conforms to the pattern.
        return pattern.matcher(password).matches();
    }
}
