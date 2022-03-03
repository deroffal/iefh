package fr.deroffal.iefh.application;

import fr.deroffal.iefh.domain.etablissement.Etablissement;
import fr.deroffal.iefh.domain.etablissement.EtablissementService;
import fr.deroffal.iefh.domain.index.Index;
import fr.deroffal.iefh.domain.index.IndexService;
import fr.deroffal.iefh.domain.reporting.ReportingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Component
@RequiredArgsConstructor
public class IefhRunner implements ApplicationRunner {

    private final EtablissementService etablissementService;
    private final IndexService indexService;

    private final ReportingService reportingService;

    @Override
    public void run(final ApplicationArguments args) {
        final List<Etablissement> etablissements = etablissementService.chargerEtablissements();
        final Set<String> sirens = etablissements.stream().map(Etablissement::getSiren).collect(toSet());

        final List<Index> indexPourSiren = indexService.chargerIndexPourEtablissementDansSiren(sirens);

        reportingService.produireCsvNotes(indexPourSiren);

        reportingService.produireCsvEtablissementsNonTrouvesPourDepartements(List.of(44), etablissements, indexPourSiren);
    }

}
