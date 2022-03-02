package fr.deroffal.iefh.domain.index;

import lombok.Data;

@Data
public class Index {
    private final String raisonSociale;
    private final String siren;
    private final Integer annee;
    private final Integer note;
    private final String structure;
    private final String nomUES;
    private final String entrepriseUES;
    private final String region;
    private final String departement;
}
