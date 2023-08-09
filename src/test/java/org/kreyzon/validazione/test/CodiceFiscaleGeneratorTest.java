package org.kreyzon.validazione.test;

import org.junit.jupiter.api.Test;
import org.kreyzon.validazione.datianagrafici.CodiceFiscaleGenerator;
import org.kreyzon.validazione.exception.BirthplaceInitialsNotAssociatedWithBirthplaceException;
import org.kreyzon.validazione.exception.BirthplaceInitialsNotFoundException;
import org.kreyzon.validazione.model.Person;

import static org.junit.jupiter.api.Assertions.*;

class CodiceFiscaleGeneratorTest {

    @Test
    void testGenerateCodiceFiscale() throws BirthplaceInitialsNotAssociatedWithBirthplaceException, BirthplaceInitialsNotFoundException {
        // Create a test Person object
        Person person = new Person("GIOVANNI", "ROSSI", "15/06/1990", "ROMA", "RM", "M");  // Rome

        // Expected codice fiscale
        String expectedCodiceFiscale = "RSSGNN90H15H501T";

        // Generate the codice fiscale
        String generatedCodiceFiscale = CodiceFiscaleGenerator.generateCodiceFiscale(person);

        // Compare with the expected value
        assertEquals(expectedCodiceFiscale, generatedCodiceFiscale);
    }

    @Test
    void testGenerateCodiceFiscale2() throws BirthplaceInitialsNotAssociatedWithBirthplaceException, BirthplaceInitialsNotFoundException {
        // Create a test Person object
        Person person = new Person("GIOVANNI", "ROSSI", "15/06/1990", "ROMA", "RM", "M");  // Rome

        // Expected codice fiscale
        String expectedCodiceFiscale = "RSSGNN90H15H501T";

        // Generate the codice fiscale
        String generatedCodiceFiscale = CodiceFiscaleGenerator.generateCodiceFiscale(person);

        // Compare with the expected value
        assertEquals(expectedCodiceFiscale, generatedCodiceFiscale);
    }

    @Test
    void testGenerateCodiceFiscale3() throws BirthplaceInitialsNotAssociatedWithBirthplaceException, BirthplaceInitialsNotFoundException {
        // Create a test Person object
        Person person = new Person("SARA", "VALENTE", "12/01/2003", "BENEVENTO", "BN", "F");  // Rome

        // Expected codice fiscale
        String expectedCodiceFiscale = "VLNSRA03A52A783K";

        // Generate the codice fiscale
        String generatedCodiceFiscale = CodiceFiscaleGenerator.generateCodiceFiscale(person);

        // Compare with the expected value
        assertEquals(expectedCodiceFiscale, generatedCodiceFiscale);
    }

    @Test
    void testGenerateCodiceFiscale4() throws BirthplaceInitialsNotAssociatedWithBirthplaceException, BirthplaceInitialsNotFoundException {
        // Create a test Person object
        Person person = new Person("MARIO", "BIANCHI", "03/09/1985", "MILANO", "MI", "M");  // Milan

        // Expected codice fiscale
        String expectedCodiceFiscale = "BNCMRA85P03F205R";

        // Generate the codice fiscale
        String generatedCodiceFiscale = CodiceFiscaleGenerator.generateCodiceFiscale(person);

        // Compare with the expected value
        assertEquals(expectedCodiceFiscale, generatedCodiceFiscale);
    }

    @Test
    void testGenerateCodiceFiscale5() throws BirthplaceInitialsNotAssociatedWithBirthplaceException, BirthplaceInitialsNotFoundException {
        // Create a test Person object
        Person person = new Person("LUCA", "FERRARI", "27/11/1992", "TORINO", "TO", "M");  // Turin

        // Expected codice fiscale
        String expectedCodiceFiscale = "FRRLCU92S27L219K";

        // Generate the codice fiscale
        String generatedCodiceFiscale = CodiceFiscaleGenerator.generateCodiceFiscale(person);

        // Compare with the expected value
        assertEquals(expectedCodiceFiscale, generatedCodiceFiscale);
    }

    @Test
    void testGenerateCodiceFiscale6() throws BirthplaceInitialsNotAssociatedWithBirthplaceException, BirthplaceInitialsNotFoundException {
        // Create a test Person object
        Person person = new Person("MARIA", "ROMANO", "18/04/1980", "NAPOLI", "NA", "F");  // Naples

        // Expected codice fiscale
        String expectedCodiceFiscale = "RMNMRA80D58F839C";

        // Generate the codice fiscale
        String generatedCodiceFiscale = CodiceFiscaleGenerator.generateCodiceFiscale(person);

        // Compare with the expected value
        assertEquals(expectedCodiceFiscale, generatedCodiceFiscale);
    }

    @Test
    void testGenerateCodiceFiscaleWithNonExistentBirthplaceInitials() {
        // Create a test Person object with non-existent birthplace initials
        Person person = new Person("LUCA", "VERDI", "10/11/1978", "MILANO", "XX", "M");  // Non-existent birthplace

        // Test for BirthplaceInitialsNotFoundException
        assertThrows(BirthplaceInitialsNotFoundException.class, () -> {
            CodiceFiscaleGenerator.generateCodiceFiscale(person);
        });
    }
}

