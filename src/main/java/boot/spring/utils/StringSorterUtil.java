package boot.spring.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StringSorterUtil {

    /**
     * Sorts a list of strings based on the numerical value of the last 13 characters in each string.
     *
     * @param strings The list of strings to be sorted.
     * @return A sorted list of strings, where each string has its last 13 characters removed.
     */
    public static List<String> sortStringsByLastDigits(List<String> strings) {
        Collections.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // Extract and parse the last 13 digits
                long num1 = Long.parseLong(o1.substring(o1.length() - 13));
                long num2 = Long.parseLong(o2.substring(o2.length() - 13));
                return Long.compare(num1, num2);
            }
        });
        List<String> modifiedStrings = new ArrayList<>();
        for (String s : strings) {
            modifiedStrings.add(s.substring(0, s.length() - 13));
        }

        return modifiedStrings; // Return the modified list
    }
}
