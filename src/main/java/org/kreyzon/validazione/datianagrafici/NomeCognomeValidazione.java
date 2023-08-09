package org.kreyzon.validazione.datianagrafici;

import org.kreyzon.validazione.RegexUtils;

public class NomeCognomeValidazione {



    public static Boolean isNomeValido(String nome) {
        return nome.matches(RegexUtils.NOME_REGEX);
    }

    public static Boolean isCognomeValido(String cognome) {
        return cognome.matches(RegexUtils.COGNOME_REGEX);
    }
}
