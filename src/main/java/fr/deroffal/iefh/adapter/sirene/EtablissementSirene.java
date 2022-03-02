package fr.deroffal.iefh.adapter.sirene;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EtablissementSirene {

    private long id;
    private String siren;
    private String siret;

    @JsonProperty("tranche_effectifs")
    private String trancheEffectifs;
    @JsonProperty("annee_effectifs")
    private String anneeEffectifs;
    @JsonProperty("activite_principale")
    private String activitePrincipale;
    @JsonProperty("code_commune")
    private String codePostal;
    @JsonProperty("unite_legale")
    private UniteLegale uniteLegale;
}
