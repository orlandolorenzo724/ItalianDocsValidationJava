package org.kreyzon.validazione.test;

import org.junit.jupiter.api.Test;
import org.kreyzon.validazione.datianagrafici.NomeCognomeValidazione;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NomeCognomeValidazioneTest {

    @Test
    public void testIsNomeValido_ValidNome() {
        assertTrue(NomeCognomeValidazione.isNomeValido("Mario"));
    }

    @Test
    public void testIsNomeValido_NomeConAccenti() {
        assertTrue(NomeCognomeValidazione.isNomeValido("Andrèa"));
    }

    @Test
    public void testIsNomeValido_NomeConSpazio() {
        assertTrue(NomeCognomeValidazione.isNomeValido("Maria Elena"));
    }

    @Test
    public void testIsNomeValido_NomeNonValido() {
        assertFalse(NomeCognomeValidazione.isNomeValido("12345"));
    }

    @Test
    public void testIsCognomeValido_ValidCognome() {
        assertTrue(NomeCognomeValidazione.isCognomeValido("Rossi"));
    }

    @Test
    public void testIsCognomeValido_CognomeConApostrofo() {
        assertTrue(NomeCognomeValidazione.isCognomeValido("D'Auria"));
    }

    @Test
    public void testIsCognomeValido_CognomeNonValido() {
        assertFalse(NomeCognomeValidazione.isCognomeValido("12345"));
    }
}