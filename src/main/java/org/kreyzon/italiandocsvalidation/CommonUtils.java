package org.kreyzon.italiandocsvalidation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kreyzon.italiandocsvalidation.exception.BirthplaceInitialsNotFoundException;
import org.kreyzon.italiandocsvalidation.model.Birthplace;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonUtils {

    public static List<Birthplace> getAllBirthplaces() {
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

    private static Map<String, String> provinceMap = new HashMap<>();

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

    public static String getBirthplaceCodeByBirthplaceName(String value) {
        for (Map.Entry<String, String> entry : provinceMap.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static String getBirthplaceValue(String key) throws BirthplaceInitialsNotFoundException {
        Map<String, String> provinceMap = getProvinceMap();
        if (provinceMap.containsKey(key)) {
            return provinceMap.get(key);
        } else {
            throw new BirthplaceInitialsNotFoundException("Birthplace initials '" + key + "' not found.");
        }
    }
}
