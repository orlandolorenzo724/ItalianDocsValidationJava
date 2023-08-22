package org.kreyzon.italiandocsvalidation.datianagrafici;

import org.apache.commons.lang3.StringUtils;
import org.kreyzon.italiandocsvalidation.CommonUtils;
import org.kreyzon.italiandocsvalidation.exception.*;
import org.kreyzon.italiandocsvalidation.model.Birthplace;
import org.kreyzon.italiandocsvalidation.model.Country;
import org.kreyzon.italiandocsvalidation.model.Gender;
import org.kreyzon.italiandocsvalidation.model.Person;
import org.kreyzon.italiandocsvalidation.utils.MonthConverter;
import org.kreyzon.italiandocsvalidation.utils.RegexUtils;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.kreyzon.italiandocsvalidation.CommonUtils.*;
import static org.kreyzon.italiandocsvalidation.utils.MonthConverter.convertMonthToLetter;
import static org.kreyzon.italiandocsvalidation.utils.RegexUtils.isValidDateFormat;

public class CodiceFiscale {
    private static List<String> vowels = List.of( "A", "E", "I", "O", "U" );

    public static String generateCodiceFiscale(Person person) {
        String lastName = extractLastName(person.getLastName());
        String firstName = extractFirstName(person.getFirstName());
        String yearOfBirth = extractYearOfBirth(person.getDateOfBirth());
        String monthOfBirth = extractMonthOfBirth(person.getDateOfBirth());
        String dayOfBirth = extractDayOfBirth(person.getDateOfBirth(), person.getGender());

        String birthplaceInitials = "";
        if (person.getBirthplaceInitials() != null)
            birthplaceInitials = person.getBirthplaceInitials();
        String birthPlace = null;
        try {
            birthPlace = extractBirthplace(person.getBirthplace(), birthplaceInitials);
        } catch (BirthplaceInitialsNotFoundException e) {
            throw new RuntimeException(e);
        } catch (BirthplaceInitialsNotAssociatedWithBirthplaceException e) {
            throw new RuntimeException(e);
        }

        String codiceFiscale = lastName + firstName + yearOfBirth + monthOfBirth + dayOfBirth + birthPlace;
        codiceFiscale += extractControlCode(codiceFiscale);

        return codiceFiscale.toUpperCase();
    }

    public static String extractFirstName(String firstName) {
        if (!isFirstNameValid(firstName))
            try {
                throw new FirstNameNotValidException("First name is not valid");
            } catch (FirstNameNotValidException e) {
                throw new RuntimeException(e);
            }

        if (StringUtils.isBlank(firstName))
            try {
                throw new FirstNameNotValidException("First name must not be empty");
            } catch (FirstNameNotValidException e) {
                throw new RuntimeException(e);
            }

        firstName = firstName.trim();
        firstName = firstName.replaceAll("'", "");

        firstName = Normalizer.normalize(firstName, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        firstName = firstName.toUpperCase();

        int consonantCount = 0;
        int vowelCount = 0;

        String firstNameConsonants = "";
        String firstNameVowels = "";

        for(char c : firstName.toCharArray()) {
            if (Character.isLetter(c)) {
                String character = Character.toString(c);

                if (vowels.contains(character)) {
                    vowelCount++;
                    firstNameVowels += character;
                } else {
                    consonantCount++;
                    firstNameConsonants += character;
                }
            }
        }

        String result = "";

        if (consonantCount > 3) {
            result = Character.toString(firstNameConsonants.charAt(0)) +
                    Character.toString(firstNameConsonants.charAt(2)) +
                    Character.toString(firstNameConsonants.charAt(3));
        } else {
            if (consonantCount == 1) {
                result = Character.toString(firstNameConsonants.charAt(0));
                if (vowelCount == 0)
                    result += "XX";
                else if (vowelCount == 1)
                    result += Character.toString(firstNameVowels.charAt(0)) + "X";
                else if (vowelCount >= 2)
                    result += Character.toString(firstNameVowels.charAt(0)) + Character.toString(firstNameVowels.charAt(1));
            } else if (consonantCount == 2) {
                result = Character.toString(firstNameConsonants.charAt(0)) + Character.toString(firstNameConsonants.charAt(1));
                if (vowelCount == 0)
                    result += "X";
                else if (vowelCount >= 1)
                    result += Character.toString(firstNameVowels.charAt(0));
            } else if (consonantCount == 3) {
                result = Character.toString(firstNameConsonants.charAt(0)) + Character.toString(firstNameConsonants.charAt(1)) + Character.toString(firstNameConsonants.charAt(2));
            }
        }

        return result;
    }

    public static String extractLastName(String lastName)  {
        if (!isLastNameValid(lastName))
            try {
                throw new LastNameNotValidException("Last name is not valid");
            } catch (LastNameNotValidException e) {
                throw new RuntimeException(e);
            }

        if (StringUtils.isBlank(lastName))
            try {
                throw new LastNameNotValidException("Last name must not be empty");
            } catch (LastNameNotValidException e) {
                throw new RuntimeException(e);
            }

        lastName = lastName.trim();
        lastName = lastName.replaceAll("'", "");

        lastName = Normalizer.normalize(lastName, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        lastName = lastName.toUpperCase();

        int consonantCount = 0;
        int vowelCount = 0;

        String firstNameConsonants = "";
        String firstNameVowels = "";

        for(char c : lastName.toCharArray()) {
            if (Character.isLetter(c)) {
                String character = Character.toString(c);

                if (vowels.contains(character)) {
                    vowelCount++;
                    firstNameVowels += character;
                } else {
                    consonantCount++;
                    firstNameConsonants += character;
                }
            }
        }

        String result = "";

        if (consonantCount >= 3) {
            result = Character.toString(firstNameConsonants.charAt(0)) +
                    Character.toString(firstNameConsonants.charAt(1)) +
                    Character.toString(firstNameConsonants.charAt(2));
        } else {
            if (consonantCount == 1) {
                result = Character.toString(firstNameConsonants.charAt(0));
                if (vowelCount == 0)
                    result += "XX";
                else if (vowelCount == 1)
                    result += Character.toString(firstNameVowels.charAt(0)) + "X";
                else if (vowelCount >= 2)
                    result += Character.toString(firstNameVowels.charAt(0)) + Character.toString(firstNameVowels.charAt(1));
            } else if (consonantCount == 2) {
                result = Character.toString(firstNameConsonants.charAt(0)) + Character.toString(firstNameConsonants.charAt(1));
                if (vowelCount == 0)
                    result += "X";
                else if (vowelCount >= 1)
                    result += Character.toString(firstNameVowels.charAt(0));
            }
        }

        return result;
    }

    public static String extractYearOfBirth(String dateOfBirth) {
        if (!isValidDateFormat(dateOfBirth)) {
            throw new IllegalArgumentException("Invalid date of birth format: " + dateOfBirth);
        }

        LocalDate dob = parseDate(dateOfBirth);
        int currentYear = LocalDate.now().getYear();
        int minYear = currentYear - 120;

        if (dob.getYear() < minYear || dob.getYear() > currentYear) {
            throw new IllegalArgumentException("Date of birth is not within the valid range.");
        }

        return String.valueOf(dob.getYear()).substring(2);
    }

    private static LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }

    public static String extractMonthOfBirth(String dateOfBirth) {
        String[] parts = dateOfBirth.split("/");
        String month = parts[1];

        return convertMonthToLetter(month);
    }

    public static String extractDayOfBirth(String dateOfBirth, String gender) {
        if (gender.equalsIgnoreCase(Gender.M.name()) || gender.equalsIgnoreCase(Gender.F.name())) {

            String[] parts = dateOfBirth.split("/");
            Integer day = Integer.parseInt(parts[0]);

            if (gender.equalsIgnoreCase(Gender.F.name()))
                return String.valueOf(day += 40);

            if (day < 10)
                return "0" + String.valueOf(day);

            return String.valueOf(day);
        }

        try {
            throw new IllegalGenderException("Gender must be M or F");
        } catch (IllegalGenderException e) {
            throw new RuntimeException(e);
        }
    }

    public static String extractBirthplace(String birthplace, String birthplaceInitials) throws BirthplaceInitialsNotFoundException, BirthplaceInitialsNotAssociatedWithBirthplaceException{
        List<Birthplace> birthplaceList = getAllBirthplaces();

        Birthplace birthplaceObj = null;

        String birthPlaceCode = "";
        boolean isItalian = false;

        if (StringUtils.isNotBlank(birthplaceInitials)) {
            // Checking if birthplaceInitials are correct
            getBirthplaceValue(birthplaceInitials);

            for (Birthplace bp : birthplaceList) {
                if (bp.getComune().equalsIgnoreCase(birthplace)) {
                    birthplaceObj = bp;
                    birthPlaceCode = bp.getCod_fisco();
                    isItalian = true;
                    break;
                }
            }

            if ( birthplaceObj != null && !birthplaceObj.getProvincia().equalsIgnoreCase(birthplaceInitials))
                throw new BirthplaceInitialsNotAssociatedWithBirthplaceException("Birth place initials " + birthplaceInitials + " is not associated with birthplace " + birthplace);
        }

        if (!isItalian) {
            List<Country> countryList = getAllCountries();
            for (Country country : countryList) {
                if (country.getName().equalsIgnoreCase(birthplace)) {
                    birthPlaceCode = country.getCode_at();
                }
            }
        }

        return birthPlaceCode.toString();

    }

    public static String extractControlCode(String codiceFiscale) {
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int[] even = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
                13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};
        int[] odd = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13, 15, 17, 19,
                21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23};

        int cont = 0;
        for (int i = 0; i < codiceFiscale.length(); i++) {
            int index = characters.indexOf(codiceFiscale.charAt(i));
            if (index != -1) {
                cont += (i % 2 == 0) ? odd[index] : even[index];
            }
        }

        int controlIndex = (cont % 26 + 10) % 36;
        return Character.toString(characters.charAt(controlIndex));
    }

    public static Boolean isFirstNameValid(String firstName) {
        if (firstName.matches(RegexUtils.AT_LEAST_ONE_VOCAL_REGEX))
            try {
                throw new AtLeastOneVocalException("First name must contain at least one vocal");
            } catch (AtLeastOneVocalException e) {
                throw new RuntimeException(e);
            }

        if (firstName.matches(RegexUtils.AT_LEAST_ONE_CONSONANT_REGEX))
            try {
                throw new AtLeastOneConsonantException("First name must contain at least one consontant");
            } catch (AtLeastOneConsonantException e) {
                throw new RuntimeException(e);
            }

        if (firstName.matches(RegexUtils.MAX_CHARS_REGEX))
            try {
                throw new CharsException("First name must be between 2 and 40 characters");
            } catch (CharsException e) {
                throw new RuntimeException(e);
            }

        return true;
    }

    public static Boolean isLastNameValid(String lastName) {
        if (lastName.matches(RegexUtils.AT_LEAST_ONE_VOCAL_REGEX))
            try {
                throw new AtLeastOneVocalException("Last name must contain at least one vocal");
            } catch (AtLeastOneVocalException e) {
                throw new RuntimeException(e);
            }

        if (lastName.matches(RegexUtils.AT_LEAST_ONE_CONSONANT_REGEX))
            try {
                throw new AtLeastOneConsonantException("Last name must contain at least one consontant");
            } catch (AtLeastOneConsonantException e) {
                throw new RuntimeException(e);
            }

        if (lastName.matches(RegexUtils.MAX_CHARS_REGEX))
            try {
                throw new CharsException("Last name must be between 2 and 40 characters");
            } catch (CharsException e) {
                throw new RuntimeException(e);
            }

        int count = 0;
        int index = -1;

        while ((index = lastName.indexOf("'", index + 1)) != -1) {
            count++;
            if (count > 1) {
                break;
            }
        }

        if (count > 1) {
            throw new IllegalStateException("Last name contains more than one '");
        }

        return true;
    }

    public static void main(String[] args) {
        Person person = Person
                .builder()
                .firstName("Mario")
                .lastName("Rossi")
                .birthplace("Milano")
                .birthplaceInitials("MI")
                .gender("M")
                .dateOfBirth("25/05/1984")
                .build();
        String codiceFiscale = generateCodiceFiscale(person);
        System.out.println("Codice fiscale: " + codiceFiscale);

        String reverseCodiceFiscale = reverseCodiceFiscale(codiceFiscale);
        System.out.println("Reverse codice fiscale: " + reverseCodiceFiscale);
    }

    public static String reverseCodiceFiscale(String codiceFiscale) {
        String firstName = reverseExtractFirstName(codiceFiscale);
        String lastName = reverseExtractLastName(codiceFiscale);
        String yearOfBirth = reverseExtractYearOfBirth(codiceFiscale);
        String monthOfBirth = reverseExtractMonthOfBirth(codiceFiscale);
        String dayOfBirth = reverseExtractDayOfBirth(codiceFiscale);
        String birthplace = reverseBirthplace(codiceFiscale).get(0);
        String birthplaceProvince = reverseBirthplace(codiceFiscale).get(1);

        String controlCode = reverseControlCode(codiceFiscale);

        return lastName + " " + firstName + " " + yearOfBirth + " " + monthOfBirth + " " + dayOfBirth + " " + birthplaceProvince + " " + birthplace;
    }

    private static String reverseExtractMonthOfBirth(String codiceFiscale) {
        return MonthConverter.convertLetterToMonth(codiceFiscale.substring(8, 9));
    }

    private static String reverseExtractDayOfBirth(String codiceFiscale) {
        String dayOfBirth = codiceFiscale.substring(9, 11);
        return dayOfBirth;
    }

    private static String reverseExtractYearOfBirth(String codiceFiscale) {
        Integer codiceFiscaleYear = Integer.parseInt(codiceFiscale.substring(6, 8));

        Integer currentYear = LocalDateTime.now().getYear();

        String codiceFiscaleYearStr = String.valueOf(codiceFiscaleYear);
        if (codiceFiscaleYear < 10)
            codiceFiscaleYearStr = "0" + String.valueOf(codiceFiscaleYear);

        if (codiceFiscaleYear > currentYear) {
            return "19" + String.valueOf(codiceFiscaleYearStr);
        } else {
            return "20" + String.valueOf(codiceFiscaleYearStr);
        }
    }

    private static String reverseExtractLastName(String codiceFiscale) {
        return codiceFiscale.substring(0, 3);
    }

    private static String reverseExtractFirstName(String codiceFiscale) {
        return codiceFiscale.substring(3, 6);
    }

    public static String reverseControlCode(String codiceFiscale) {
        return codiceFiscale.substring(codiceFiscale.length() - 1);
    }

    private static List<String> reverseBirthplace(String codiceFiscale) {
        List<String> returnList = new ArrayList<>();

        String birthplaceCode = codiceFiscale.substring(11, 15);

        List<Birthplace> birthplaceList = getAllBirthplaces();

        for (Birthplace birthplace : birthplaceList) {
            if (birthplace.getCod_fisco().equalsIgnoreCase(birthplaceCode)) {
                returnList.add(birthplace.getComune());
                returnList.add(CommonUtils.getBirthplaceCodeByBirthplaceName(birthplace.getComune()));
                return returnList;
            }
        }

        throw new IllegalStateException("Couldn't retriever birthplace from codice fiscale " + codiceFiscale);
    }

    // BNC MRA 85 P 03 F205 R
}