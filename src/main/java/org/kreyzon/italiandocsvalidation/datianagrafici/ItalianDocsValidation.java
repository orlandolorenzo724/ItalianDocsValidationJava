package org.kreyzon.italiandocsvalidation.datianagrafici;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.kreyzon.italiandocsvalidation.exception.*;
import org.kreyzon.italiandocsvalidation.model.Birthplace;
import org.kreyzon.italiandocsvalidation.model.Person;
import org.kreyzon.italiandocsvalidation.utils.MonthConverter;
import org.kreyzon.italiandocsvalidation.utils.RegexUtils;

import java.io.File;
import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.kreyzon.italiandocsvalidation.utils.MonthConverter.convertMonthToLetter;
import static org.kreyzon.italiandocsvalidation.utils.RegexUtils.isValidDateFormat;

public class ItalianDocsValidation {
    private static List<String> vowels = List.of("A", "E", "I", "O", "U" );

    private static Map<String, String> provinceMap = new HashMap<>();


    public static String CFGenerateCodiceFiscale(Person person) {
        String lastName = CFExtractLastName(person.getLastName());
        String firstName = CFExtractFirstName(person.getFirstName());
        String yearOfBirth = CFExtractYearOfBirth(person.getDateOfBirth());
        String monthOfBirth = CFExtractMonthOfBirth(person.getDateOfBirth());
        String dayOfBirth = CFExtractDayOfBirth(person.getDateOfBirth(), person.getGender());

        String birthplaceInitials = person.getBirthplaceInitials();
        String birthPlace = null;
        try {
            birthPlace = CFExtractBirthplace(person.getBirthplace(), birthplaceInitials);
        } catch (BirthplaceInitialsNotFoundException e) {
            throw new RuntimeException(e);
        } catch (BirthplaceInitialsNotAssociatedWithBirthplaceException e) {
            throw new RuntimeException(e);
        }

        String codiceFiscale = lastName + firstName + yearOfBirth + monthOfBirth + dayOfBirth + birthPlace;
        codiceFiscale += GEOextractControlCode(codiceFiscale);

        return codiceFiscale.toUpperCase();
    }

    public static String CFExtractFirstName(String firstName) {
        if (!CFisFirstNameValid(firstName))
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

    public static String CFExtractLastName(String lastName)  {
        if (!CFisLastNameValid(lastName))
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

    public static String CFExtractYearOfBirth(String dateOfBirth) {
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

    public static String CFExtractMonthOfBirth(String dateOfBirth) {
        String[] parts = dateOfBirth.split("/");
        String month = parts[1];

        return convertMonthToLetter(month);
    }

    public static String CFExtractDayOfBirth(String dateOfBirth, String gender) {
        if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F")) {

            String[] parts = dateOfBirth.split("/");
            Integer day = Integer.parseInt(parts[0]);

            if (gender.equalsIgnoreCase("F"))
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

    public static String CFExtractBirthplace(String birthplace, String birthplaceInitials) throws BirthplaceInitialsNotFoundException, BirthplaceInitialsNotAssociatedWithBirthplaceException{
        List<Birthplace> birthplaceList = GEOgetAllBirthplaces();

        Birthplace birthplaceObj = null;

        // Checking if birthplaceInitials are correct
        getBirthplaceValue(birthplaceInitials);

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

    }

    public static List<Birthplace> GEOgetAllBirthplaces() {
        ObjectMapper objectMapper = new ObjectMapper();

        List<Birthplace> birthplaceList = null;
        try {
            birthplaceList = objectMapper.readValue(new File("src/main/resources/comuni.json"),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Birthplace.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return birthplaceList;
    }

    public static String GEOextractControlCode(String codiceFiscale) {
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

    public static Boolean CFisFirstNameValid(String firstName) {
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

    public static Boolean CFisLastNameValid(String lastName) {
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

    public static void main(String[] args) throws BirthplaceInitialsNotAssociatedWithBirthplaceException, BirthplaceInitialsNotFoundException, FirstNameNotValidException, LastNameNotValidException {
        Person person = Person
                .builder()
                .firstName("Lorenzo")
                .lastName("Orlando")
                .birthplace("Napoli")
                .birthplaceInitials("NA")
                .gender("M")
                .dateOfBirth("28/03/2001")
                .build();
        String codiceFiscale = CFGenerateCodiceFiscale(person);
        System.out.println("Codice fiscale: " + codiceFiscale);

        String reverseCodiceFiscale = CFReverseCodiceFiscale(codiceFiscale);
        System.out.println("Reverse codice fiscale: " + reverseCodiceFiscale);

    }

    public static Map<String, String> getProvinceMap() {

        // Insert data into the map
        provinceMap.put("AG", "Agrigento");
        provinceMap.put("AL", "Alessandria");
        provinceMap.put("AN", "Ancona");
        provinceMap.put("AO", "Aosta");
        provinceMap.put("AQ", "L'Aquila");
        provinceMap.put("AR", "Arezzo");
        provinceMap.put("AP", "Ascoli-Piceno");
        provinceMap.put("AT", "Asti");
        provinceMap.put("AV", "Avellino");
        provinceMap.put("BA", "Bari");
        provinceMap.put("BT", "Barletta-Andria-Trani");
        provinceMap.put("BL", "Belluno");
        provinceMap.put("BN", "Benevento");
        provinceMap.put("BG", "Bergamo");
        provinceMap.put("BI", "Biella");
        provinceMap.put("BO", "Bologna");
        provinceMap.put("BZ", "Bolzano");
        provinceMap.put("BS", "Brescia");
        provinceMap.put("BR", "Brindisi");
        provinceMap.put("CA", "Cagliari");
        provinceMap.put("CL", "Caltanissetta");
        provinceMap.put("CB", "Campobasso");
        provinceMap.put("CI", "Carbonia Iglesias");
        provinceMap.put("CE", "Caserta");
        provinceMap.put("CT", "Catania");
        provinceMap.put("CZ", "Catanzaro");
        provinceMap.put("CH", "Chieti");
        provinceMap.put("CO", "Como");
        provinceMap.put("CS", "Cosenza");
        provinceMap.put("CR", "Cremona");
        provinceMap.put("KR", "Crotone");
        provinceMap.put("CN", "Cuneo");
        provinceMap.put("EN", "Enna");
        provinceMap.put("FM", "Fermo");
        provinceMap.put("FE", "Ferrara");
        provinceMap.put("FI", "Firenze");
        provinceMap.put("FG", "Foggia");
        provinceMap.put("FC", "Forli-Cesena");
        provinceMap.put("FR", "Frosinone");
        provinceMap.put("GE", "Genova");
        provinceMap.put("GO", "Gorizia");
        provinceMap.put("GR", "Grosseto");
        provinceMap.put("IM", "Imperia");
        provinceMap.put("IS", "Isernia");
        provinceMap.put("SP", "La-Spezia");
        provinceMap.put("LT", "Latina");
        provinceMap.put("LE", "Lecce");
        provinceMap.put("LC", "Lecco");
        provinceMap.put("LI", "Livorno");
        provinceMap.put("LO", "Lodi");
        provinceMap.put("LU", "Lucca");
        provinceMap.put("MC", "Macerata");
        provinceMap.put("MN", "Mantova");
        provinceMap.put("MS", "Massa-Carrara");
        provinceMap.put("MT", "Matera");
        provinceMap.put("VS", "Medio Campidano");
        provinceMap.put("ME", "Messina");
        provinceMap.put("MI", "Milano");
        provinceMap.put("MO", "Modena");
        provinceMap.put("MB", "Monza-Brianza");
        provinceMap.put("NA", "Napoli");
        provinceMap.put("NO", "Novara");
        provinceMap.put("NU", "Nuoro");
        provinceMap.put("OG", "Ogliastra");
        provinceMap.put("OT", "Olbia Tempio");
        provinceMap.put("OR", "Oristano");
        provinceMap.put("PD", "Padova");
        provinceMap.put("PA", "Palermo");
        provinceMap.put("PR", "Parma");
        provinceMap.put("PV", "Pavia");
        provinceMap.put("PG", "Perugia");
        provinceMap.put("PU", "Pesaro-Urbino");
        provinceMap.put("PE", "Pescara");
        provinceMap.put("PC", "Piacenza");
        provinceMap.put("PI", "Pisa");
        provinceMap.put("PT", "Pistoia");
        provinceMap.put("PN", "Pordenone");
        provinceMap.put("PZ", "Potenza");
        provinceMap.put("PO", "Prato");
        provinceMap.put("RG", "Ragusa");
        provinceMap.put("RA", "Ravenna");
        provinceMap.put("RC", "Reggio-Calabria");
        provinceMap.put("RE", "Reggio-Emilia");
        provinceMap.put("RI", "Rieti");
        provinceMap.put("RN", "Rimini");
        provinceMap.put("RM", "Roma");
        provinceMap.put("RO", "Rovigo");
        provinceMap.put("SA", "Salerno");
        provinceMap.put("SS", "Sassari");
        provinceMap.put("SV", "Savona");
        provinceMap.put("SI", "Siena");
        provinceMap.put("SR", "Siracusa");
        provinceMap.put("SO", "Sondrio");
        provinceMap.put("TA", "Taranto");
        provinceMap.put("TE", "Teramo");
        provinceMap.put("TR", "Terni");
        provinceMap.put("TO", "Torino");
        provinceMap.put("TP", "Trapani");
        provinceMap.put("TN", "Trento");
        provinceMap.put("TV", "Treviso");
        provinceMap.put("TS", "Trieste");
        provinceMap.put("UD", "Udine");
        provinceMap.put("VA", "Varese");
        provinceMap.put("VE", "Venezia");
        provinceMap.put("VB", "Verbania");
        provinceMap.put("VC", "Vercelli");
        provinceMap.put("VR", "Verona");
        provinceMap.put("VV", "Vibo-Valentia");
        provinceMap.put("VI", "Vicenza");
        provinceMap.put("VT", "Viterbo");

        return provinceMap;
    }

    public boolean doesBirthplaceInitialsExist(String birthplaceInitials) {
        Map<String, String> provinceMap = getProvinceMap();
        return provinceMap.containsKey(birthplaceInitials);
    }

    public static String getBirthplaceValue(String key) throws BirthplaceInitialsNotFoundException {
        Map<String, String> provinceMap = getProvinceMap();
        if (provinceMap.containsKey(key)) {
            return provinceMap.get(key);
        } else {
            throw new BirthplaceInitialsNotFoundException("Birthplace initials '" + key + "' not found.");
        }
    }

    public static String CFReverseCodiceFiscale(String codiceFiscale) {
        String firstName = CFReverseExtractFirstName(codiceFiscale);
        String lastName = CFReverseExtractLastName(codiceFiscale);
        String yearOfBirth = CFReverseExtractYearOfBirth(codiceFiscale);
        String monthOfBirth = CFReverseExtractMonthOfBirth(codiceFiscale);
        String dayOfBirth = CFReverseExtractDayOfBirth(codiceFiscale);
        String controlCode = CFReverseControlCode(codiceFiscale);

        return lastName + firstName + yearOfBirth + monthOfBirth + dayOfBirth + controlCode;
    }

    private static String CFReverseExtractMonthOfBirth(String codiceFiscale) {
        return MonthConverter.convertLetterToMonth(codiceFiscale.substring(8, 9));
    }

    private static String CFReverseExtractDayOfBirth(String codiceFiscale) {
        Integer dayOfBirth = Integer.parseInt(codiceFiscale.substring(9, 11));
        if (dayOfBirth > 31)
            return "F";

        return "M";
    }

    private static String CFReverseExtractYearOfBirth(String codiceFiscale) {
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

    private static String CFReverseExtractLastName(String codiceFiscale) {
        return codiceFiscale.substring(0, 3);
    }

    private static String CFReverseExtractFirstName(String codiceFiscale) {
        return codiceFiscale.substring(3, 6);
    }

    public static String CFReverseControlCode(String codiceFiscale) {
        return codiceFiscale.substring(codiceFiscale.length() - 1);
    }

    // BNC MRA 85 P 03 F205 R
}