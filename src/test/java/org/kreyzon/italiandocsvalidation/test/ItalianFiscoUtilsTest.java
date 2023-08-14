package org.kreyzon.italiandocsvalidation.test;

import org.junit.jupiter.api.Test;
import org.kreyzon.italiandocsvalidation.datianagrafici.CodiceFiscale;
import org.kreyzon.italiandocsvalidation.exception.*;
import org.kreyzon.italiandocsvalidation.model.Person;

import static org.junit.jupiter.api.Assertions.*;

class ItalianFiscoUtilsTest {

    @Test
    void testGenerateCodiceFiscale() throws BirthplaceInitialsNotAssociatedWithBirthplaceException, BirthplaceInitialsNotFoundException, FirstNameNotValidException, LastNameNotValidException {
        // Create a test Person object
        Person person = new Person("GIOVANNI", "ROSSI", "15/06/1990", "ROMA", "RM", "M");  // Rome

        // Expected codice fiscale
        String expectedCodiceFiscale = "RSSGNN90H15H501T";

        // Generate the codice fiscale
        String generatedCodiceFiscale = CodiceFiscale.generateCodiceFiscale(person);

        // Compare with the expected value
        assertEquals(expectedCodiceFiscale, generatedCodiceFiscale);
    }

    @Test
    void testGenerateCodiceFiscale2() throws BirthplaceInitialsNotAssociatedWithBirthplaceException, BirthplaceInitialsNotFoundException, FirstNameNotValidException, LastNameNotValidException {
        // Create a test Person object
        Person person = new Person("Giovanni", "Rossi", "15/06/1990", "Roma", "RM", "M");  // Rome

        // Expected codice fiscale
        String expectedCodiceFiscale = "RSSGNN90H15H501T";

        // Generate the codice fiscale
        String generatedCodiceFiscale = CodiceFiscale.generateCodiceFiscale(person);

        // Compare with the expected value
        assertEquals(expectedCodiceFiscale, generatedCodiceFiscale);
    }

    @Test
    void testGenerateCodiceFiscale3() throws BirthplaceInitialsNotAssociatedWithBirthplaceException, BirthplaceInitialsNotFoundException, FirstNameNotValidException, LastNameNotValidException {
        // Create a test Person object
        Person person = new Person("Sara", "Valente", "12/01/2003", "Benevento", "BN", "F");  // Rome

        // Expected codice fiscale
        String expectedCodiceFiscale = "VLNSRA03A52A783K";

        // Generate the codice fiscale
        String generatedCodiceFiscale = CodiceFiscale.generateCodiceFiscale(person);

        // Compare with the expected value
        assertEquals(expectedCodiceFiscale, generatedCodiceFiscale);
    }

    @Test
    void testGenerateCodiceFiscale4() throws BirthplaceInitialsNotAssociatedWithBirthplaceException, BirthplaceInitialsNotFoundException, FirstNameNotValidException, LastNameNotValidException {
        // Create a test Person object
        Person person = new Person("Mario", "D'Auria", "03/09/1985", "Milano", "MI", "M");  // Milan

        // Expected codice fiscale
        String expectedCodiceFiscale = "BNCMRA85P03F205R";

        // Generate the codice fiscale
        String generatedCodiceFiscale = CodiceFiscale.generateCodiceFiscale(person);

        // Compare with the expected value
        assertNotEquals(expectedCodiceFiscale, generatedCodiceFiscale);
    }

    @Test
    void testGenerateCodiceFiscale5() throws BirthplaceInitialsNotAssociatedWithBirthplaceException, BirthplaceInitialsNotFoundException, FirstNameNotValidException, LastNameNotValidException {
        // Create a test Person object
        Person person = new Person("Luca", "Ferrari", "27/11/1992", "Torino", "TO", "M");  // Turin

        // Expected codice fiscale
        String expectedCodiceFiscale = "FRRLCU92S27L219K";

        // Generate the codice fiscale
        String generatedCodiceFiscale = CodiceFiscale.generateCodiceFiscale(person);

        // Compare with the expected value
        assertEquals(expectedCodiceFiscale, generatedCodiceFiscale);
    }

    @Test
    void testGenerateCodiceFiscale6() throws BirthplaceInitialsNotAssociatedWithBirthplaceException, BirthplaceInitialsNotFoundException, FirstNameNotValidException, LastNameNotValidException {
        // Create a test Person object
        Person person = new Person("Maria", "Romano", "18/04/1980", "Napoli", "NA", "F");  // Naples

        // Expected codice fiscale
        String expectedCodiceFiscale = "RMNMRA80D58F839C";

        // Generate the codice fiscale
        String generatedCodiceFiscale = CodiceFiscale.generateCodiceFiscale(person);

        // Compare with the expected value
        assertEquals(expectedCodiceFiscale, generatedCodiceFiscale);
    }

    @Test
    void testGenerateCodiceFiscaleForMarcoRossi() {
        Person person = new Person("Marco", "Rossi", "15/03/1985", "Milano", "MI", "M");
        String expectedCodiceFiscale = "RSSMRC85C15F205W";
        String generatedCodiceFiscale = CodiceFiscale.generateCodiceFiscale(person);
        assertEquals(expectedCodiceFiscale, generatedCodiceFiscale);
    }

    @Test
    void testGenerateCodiceFiscaleForLauraBianchi() {
        Person person = new Person("Laura", "Bianchi", "10/08/1992", "Roma", "RM", "F");
        String expectedCodiceFiscale = "BNCLRA92M50H501A";
        String generatedCodiceFiscale = CodiceFiscale.generateCodiceFiscale(person);
        assertEquals(expectedCodiceFiscale, generatedCodiceFiscale);
    }

    @Test
    void testGenerateCodiceFiscaleForLucaVerdi() {
        Person person = new Person("Luca", "Verdi", "23/06/1978", "Napoli", "NA", "M");
        String expectedCodiceFiscale = "VRDLCU78H23F839N";
        String generatedCodiceFiscale = CodiceFiscale.generateCodiceFiscale(person);
        assertEquals(expectedCodiceFiscale, generatedCodiceFiscale);
    }

    // ... Similar methods for other JSON objects ...

    @Test
    void testGenerateCodiceFiscaleForFrancescaLombardi() {
        Person person = new Person("Francesca", "Lombardi", "25/06/1975", "Brescia", "BS", "F");
        String expectedCodiceFiscale = "LMBFNC75H65B157U";
        String generatedCodiceFiscale = CodiceFiscale.generateCodiceFiscale(person);
        assertEquals(expectedCodiceFiscale, generatedCodiceFiscale);
    }

    @Test
    void testGenerateCodiceFiscaleForDavideMoretti() {
        Person person = new Person("Davide", "Moretti", "18/04/1993", "Venezia", "VE", "M");
        String expectedCodiceFiscale = "MRTDVD93D18L736G";
        String generatedCodiceFiscale = CodiceFiscale.generateCodiceFiscale(person);
        assertEquals(expectedCodiceFiscale, generatedCodiceFiscale);
    }

    // Invalid data test cases
    @Test
    void testGenerateCodiceFiscaleForInvalidData() {
        Person person = new Person("Invalid", "Data", "99/99/9999", "Nowhere", "XX", "X");
        assertThrows(Exception.class, () -> {
            CodiceFiscale.generateCodiceFiscale(person);
        });
    }

    @Test
    void testGenerateCodiceFiscaleForStrangeName() {
        Person person = new Person("Strange", "Name", "31/02/2000", "Dreamland", "DL", "M");
        assertThrows(RuntimeException.class, () -> {
            CodiceFiscale.generateCodiceFiscale(person);
        });
    }

    @Test
    void testGenerateCodiceFiscaleForWrongGender() {
        Person person = new Person("Davide", "Moretti", "18/04/1993", "Venezia", "VE", "N");
        assertThrows(RuntimeException.class, () -> {
            CodiceFiscale.generateCodiceFiscale(person);
        });
    }

    @Test
    void testGenerateCodiceFiscaleForWrongDOB() {
        Person person = new Person("Davide", "Moretti", "18/04/1900", "Venezia", "VE", "N");
        assertThrows(RuntimeException.class, () -> {
            CodiceFiscale.generateCodiceFiscale(person);
        });
    }

    @Test
    void testGenerateCodiceFiscaleWithNonExistentBirthplaceInitials() {
        // Create a test Person object with non-existent birthplace initials
        Person person = new Person("Luca", "Verdi", "10/11/1978", "Milano", "XX", "M");  // Non-existent birthplace

        // Test for BirthplaceInitialsNotFoundException
        assertThrows(Exception.class, () -> {
            CodiceFiscale.generateCodiceFiscale(person);
        });
    }
}

