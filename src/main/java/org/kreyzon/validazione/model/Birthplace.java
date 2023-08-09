package org.kreyzon.validazione.model;

import lombok.Data;

@Data
public class Birthplace {
    /** Birthplace name **/
    private String comune;

    private String cod_fisco;

    private String provincia;

    private String regione;

    private String num_residenti;

    private String superficie;

    private String prefisso;
}
