package fr.deroffal.iefh.domain.reporting;

import fr.deroffal.iefh.domain.etablissement.Etablissement;
import fr.deroffal.iefh.domain.index.Index;

import java.util.List;

public interface ReportingCsvWriterPort {

    void ecrireFichierCsvNotes(List<Index> index);

    void creerFichierAutresEtablissementsDuDepartement(String numeroDepartement, List<Etablissement> autresEtablissements);
}
