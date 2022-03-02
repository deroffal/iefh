package fr.deroffal.iefh.domain.etablissement;

import lombok.Data;

@Data
public class Etablissement {
    private final String nom;
    private final String siren;
    private final String siret;
    private final String trancheEffectifs;
    private final String anneeEffectifs;
    private final String activitePrincipale;
    private final String codePostal;
}
