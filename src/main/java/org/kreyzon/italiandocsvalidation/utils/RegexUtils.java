package org.kreyzon.italiandocsvalidation.utils;

public class RegexUtils {


    public static final String AT_LEAST_ONE_VOCAL_REGEX = "/^(?=.[aeiouAEIOU])$/";
    public static final String AT_LEAST_ONE_CONSONANT_REGEX = "/^(?=.[^aeiouAEIOU])$/";
    public static final String MAX_CHARS_REGEX = "/^[A-Za-z\\s]{2,40}$/";

    public static boolean isValidDateFormat(String dateOfBirth) {
        // Regular expression for dd/MM/yyyy format
        String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$";
        return dateOfBirth.matches(regex);
    }
}
