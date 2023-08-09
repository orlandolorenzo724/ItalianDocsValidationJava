package org.kreyzon.validazione;

public class RegexUtils {

    /**
     * ^[A-Za-zÀ-ÖØ-öø-ÿ\\s'-]+$
     *
     * ^: Questo simbolo indica l'inizio della stringa.
     * [A-Za-zÀ-ÖØ-öø-ÿ\\s'-]+: Questa parte della regex corrisponde a una sequenza di caratteri che includono:
     * A-Za-z: Tutte le lettere dell'alfabeto inglese in maiuscolo e minuscolo.
     * À-ÖØ-öø-ÿ: Caratteri accentati e speciali presenti nell'alfabeto italiano, come À, È, Ì, Ò, Ù, ecc.
     * \\s: Uno spazio vuoto.
     * '-: L'apostrofo e il trattino.
     * +: Questo simbolo indica che la sequenza di caratteri può apparire una o più volte.
     * $: Questo simbolo indica la fine della stringa.
     */
    public static final String NOME_REGEX = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s'-]+$";


    /**
     * ^[A-Za-zÀ-ÖØ-öø-ÿ\s'-]+$
     *
     * ^: Indica l'inizio della stringa.
     * [A-Za-zÀ-ÖØ-öø-ÿ\\s-]+:
     * [A-Za-zÀ-ÖØ-öø-ÿ]: Questa parte corrisponde a qualsiasi lettera dell'alfabeto italiano, inclusi caratteri accentati come À, È, Ì, Ò, Ù, ecc., sia in maiuscolo che in minuscolo.
     * \\s: Corrisponde a uno spazio vuoto.
     * -: Corrisponde direttamente al carattere trattino.
     * +: Indica che la sequenza di caratteri precedente può apparire una o più volte.
     * $: Indica la fine della stringa.
     */
    public static final String COGNOME_REGEX = "^[A-Za-zÀ-ÖØ-öø-ÿ\\s'-]+$";

    public static boolean isValidDateFormat(String dateOfBirth) {
        // Regular expression for dd/MM/yyyy format
        String regex = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$";
        return dateOfBirth.matches(regex);
    }
}
