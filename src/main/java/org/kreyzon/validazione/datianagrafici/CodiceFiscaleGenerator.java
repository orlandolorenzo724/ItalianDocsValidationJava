package org.kreyzon.validazione.datianagrafici;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.kreyzon.validazione.ProvinceMapUtils;
import org.kreyzon.validazione.exception.BirthplaceInitialsNotAssociatedWithBirthplaceException;
import org.kreyzon.validazione.exception.BirthplaceInitialsNotFoundException;
import org.kreyzon.validazione.model.Birthplace;
import org.kreyzon.validazione.model.Person;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.kreyzon.validazione.MonthConverter.convertMonthToLetter;
import static org.kreyzon.validazione.RegexUtils.isValidDateFormat;

public class CodiceFiscaleGenerator {
    private static List<String> vowels = List.of("A", "E", "I", "O", "U" );

    public static String generateCodiceFiscale(Person person) throws BirthplaceInitialsNotAssociatedWithBirthplaceException, BirthplaceInitialsNotFoundException {
        String lastName = extractLastName(person.getLastName());
        String firstName = extractFirstName(person.getFirstName());
        String yearOfBirth = extractYearOfBirth(person.getDateOfBirth());
        String monthOfBirth = extractMonthOfBirth(person.getDateOfBirth());
        String dayOfBirth = extractDayOfBirth(person.getDateOfBirth(), person.getGender());

        String birthplaceInitials = person.getBirthplaceInitials();
        String birthPlace = extractBirthplace(person.getBirthplace(), birthplaceInitials);

        String codiceFiscale = lastName + firstName + yearOfBirth + monthOfBirth + dayOfBirth + birthPlace;
        codiceFiscale += extractControlCode(codiceFiscale);

        return codiceFiscale;
    }

    public static String extractFirstName(String firstName) {
        if (StringUtils.isBlank(firstName))
            throw new IllegalArgumentException("First name must not be empty");

        firstName = firstName.trim();

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

    public static String extractLastName(String lastName) {
        if (StringUtils.isBlank(lastName))
            throw new IllegalArgumentException("Last name must not be empty");

        lastName = lastName.trim();

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

        String[] parts = dateOfBirth.split("/");
        String year = parts[2];
        return year.substring(year.length() - 2);
    }

    public static String extractMonthOfBirth(String dateOfBirth) {
        String[] parts = dateOfBirth.split("/");
        String month = parts[1];

        return convertMonthToLetter(month);
    }

    public static String extractDayOfBirth(String dateOfBirth, String gender) {
        String[] parts = dateOfBirth.split("/");
        Integer day = Integer.parseInt(parts[0]);

        if (gender.equalsIgnoreCase("F"))
            return String.valueOf(day += 40);

        if (day < 10)
            return "0" + String.valueOf(day);

        return String.valueOf(day);
    }

    public static String extractBirthplace(String birthplace, String birthplaceInitials) throws BirthplaceInitialsNotFoundException, BirthplaceInitialsNotAssociatedWithBirthplaceException{
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Birthplace> birthplaceList = objectMapper.readValue(new File("src/main/resources/comuni.json"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Birthplace.class));

            Birthplace birthplaceObj = null;

            // Checking if birthplaceInitials are correct
            ProvinceMapUtils.getBirthplaceValue(birthplaceInitials);

            String birthPlaceCode = "";

            for (Birthplace bp : birthplaceList) {
                if (bp.getComune().equalsIgnoreCase(birthplace)) {
                    birthplaceObj = bp;
                    birthPlaceCode = bp.getCod_fisco();
                    break;
                }
            }

            if ( birthplaceObj != null && !birthplaceObj.getProvincia().equalsIgnoreCase(birthplaceInitials))
                throw new BirthplaceInitialsNotAssociatedWithBirthplaceException("Birth place initials " + birthplaceInitials + " is not associated with birthplace " + birthplace);

            return birthPlaceCode.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public static void main(String[] args) throws BirthplaceInitialsNotAssociatedWithBirthplaceException, BirthplaceInitialsNotFoundException {
        Person person = Person
                .builder()
                .firstName("GABRIEL")
                .lastName("TEDDA")
                .birthplace("NAPOLI")
                .birthplaceInitials("NA")
                .gender("M")
                .dateOfBirth("11/12/1920")
                .build();
        String codiceFiscale = generateCodiceFiscale(person);
        System.out.println("Codice fiscale: " + codiceFiscale);
    }
}