package org.kreyzon.italiandocsvalidation;


public class Main {

    public static String calculateCodiceFiscale(String cognome, String nome, int annoNascita, String meseNascita,
                                                int giornoNascita, char sesso, String comuneNascita) {
        String cfCognome = calculateCognome(cognome);
        String cfNome = calculateNome(nome);
        String cfComune = comuneNascita;

        String cfAnno = String.format("%02d", annoNascita % 100);
        String cfMese = calculateMese(meseNascita);
        int cfGiorno = giornoNascita + (sesso == 'F' ? 40 : 0);
        String cfGiornoSesso = String.format("%02d", cfGiorno);

       // char cfControllo = calculateControllo(cfCognome + cfNome + cfAnno + cfMese + cfGiornoSesso + cfComune);

        return cfCognome + cfNome + cfAnno + cfMese + cfGiornoSesso + cfComune ;
    }

    public static String calculateCognome(String cognome) {
        String consonanti = extractConsonants(cognome);
        if (consonanti.length() >= 3) {
            return consonanti.substring(0, 3);
        } else {
            String vocali = extractVowels(cognome);
            return consonanti + vocali.substring(0, 3 - consonanti.length());
        }
    }

    public static String calculateNome(String nome) {
        String consonanti = extractConsonants(nome);
        if (consonanti.length() >= 4) {
            return "" + consonanti.charAt(0) + consonanti.charAt(2) + consonanti.charAt(3);
        } else {
            String vocali = extractVowels(nome);
            return consonanti + vocali.substring(0, 3 - consonanti.length());
        }
    }

    public static String calculateMese(String meseNascita) {
        String[] mesi = {"A", "B", "C", "D", "E", "H", "L", "M", "P", "R", "S", "T"};
        return mesi[getMonthIndex(meseNascita)];
    }

    public static char calculateControllo(String input) {
        int[] values = {
                1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23
        };

        int sum = 0;
        for (int i = 0; i < input.length(); i++) {
            int index = (i % 2 == 0) ? 2 * (input.charAt(i) - 'A') : values[input.charAt(i) - 'A'];
            sum += index;
        }

        int remainder = sum % 26;
        return (char) ('A' + remainder);
    }

    public static int getMonthIndex(String meseNascita) {
        String[] mesi = {"GENNAIO", "FEBBRAIO", "MARZO", "APRILE", "MAGGIO", "GIUGNO", "LUGLIO",
                "AGOSTO", "SETTEMBRE", "OTTOBRE", "NOVEMBRE", "DICEMBRE"};
        for (int i = 0; i < mesi.length; i++) {
            if (meseNascita.equalsIgnoreCase(mesi[i])) {
                return i;
            }
        }
        return -1; // Mese non valido
    }

    public static String extractConsonants(String input) {
        return input.replaceAll("[AEIOUaeiou]", "");
    }

    public static String extractVowels(String input) {
        return input.replaceAll("[^AEIOUaeiou]", "");
    }

    public static void main(String[] args) {
        String codiceFiscale = calculateCodiceFiscale("BIANCHI", "MARIO", 1970, "GENNAIO", 20, 'M', "RO");
        System.out.println("Codice Fiscale: " + codiceFiscale);
    }
}

