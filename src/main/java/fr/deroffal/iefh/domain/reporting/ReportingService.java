package fr.deroffal.iefh.domain.reporting;

import fr.deroffal.iefh.domain.etablissement.Etablissement;
import fr.deroffal.iefh.domain.index.Index;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class ReportingService {

    private final ReportingCsvWriterPort reportingCsvWriter;

    public void produireCsvNotes(final List<Index> index) {
        reportingCsvWriter.ecrireFichierCsvNotes(index);
    }

    public void produireCsvEtablissementsNonTrouvesPourDepartements(final Collection<Integer> numerosDepartements, final List<Etablissement> etablissements, final List<Index> index) {
        final Set<String> sirens = index.stream().map(Index::getSiren).collect(toSet());

        numerosDepartements.forEach(departement -> {
            final String numeroDepartement = departement.toString();

            final List<Etablissement> autresEtablissements = etablissements.stream()
                    .filter(etablissement -> isEtablissementParmiDepartementsDemandes(etablissement, numeroDepartement))
                    .filter(etablissement -> isEtablissementAbsentIndex(etablissement, sirens))
                    .filter(ReportingService::isEtablissementAvecNomRenseigne)
                    .toList();

            reportingCsvWriter.creerFichierAutresEtablissementsDuDepartement(numeroDepartement, autresEtablissements);
        });

    }

    private static boolean isEtablissementAvecNomRenseigne(final Etablissement etablissement) {
        return etablissement.getNom() != null && !etablissement.getNom().isBlank();
    }

    private static boolean isEtablissementAbsentIndex(final Etablissement etablissement, final Set<String> sirens) {
        return !sirens.contains(etablissement.getSiren());
    }

    private static boolean isEtablissementParmiDepartementsDemandes(final Etablissement etablissement, final String numeroDepartement) {
        return etablissement.getCodePostal().startsWith(numeroDepartement);
    }


}
