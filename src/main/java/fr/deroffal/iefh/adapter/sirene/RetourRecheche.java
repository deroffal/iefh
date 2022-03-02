package fr.deroffal.iefh.adapter.sirene;

import java.util.List;

import lombok.Data;

@Data
public class RetourRecheche {

    private List<EtablissementSirene> etablissements;
    private Metadonnees meta;
}
