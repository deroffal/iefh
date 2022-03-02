package fr.deroffal.iefh.domain.etablissement;

import java.util.List;

public interface EtablissementCsvWriterPort {

    void ecrireFichier(List<Etablissement> etablissements);
}
