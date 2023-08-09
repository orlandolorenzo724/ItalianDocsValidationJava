package org.kreyzon.validazione;

import java.util.HashMap;
import java.util.Map;

public class MonthConverter {

    private static final Map<String, String> monthMap = new HashMap<>();

    static {
        monthMap.put("01", "A"); // Gennaio
        monthMap.put("02", "B"); // Febbraio
        monthMap.put("03", "C"); // Marzo
        monthMap.put("04", "D"); // Aprile
        monthMap.put("05", "E"); // Maggio
        monthMap.put("06", "H"); // Giugno
        monthMap.put("07", "L"); // Luglio
        monthMap.put("08", "M"); // Agosto
        monthMap.put("09", "P"); // Settembre
        monthMap.put("10", "R"); // Ottobre
        monthMap.put("11", "S"); // Novembre
        monthMap.put("12", "T"); // Dicembre
    }

    public static String convertMonthToLetter(String month) {
        return monthMap.get(month);
    }
}
