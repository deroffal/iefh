package fr.deroffal.iefh.application;

import fr.deroffal.iefh.domain.etablissement.Etablissement;
import fr.deroffal.iefh.domain.etablissement.EtablissementService;
import fr.deroffal.iefh.domain.index.Index;
import fr.deroffal.iefh.domain.index.IndexService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

@Slf4j
@Component
@RequiredArgsConstructor
public class IefhRunner implements ApplicationRunner {

    private final EtablissementService etablissementService;
    private final IndexService indexService;

    @Override
    public void run(final ApplicationArguments args) {
        final List<Etablissement> etablissements = etablissementService.chargerEtablissements();
        final Map<String, List<Etablissement>> etablissementsParSiren = etablissements.stream().collect(groupingBy(Etablissement::getSiren));

        final List<Index> indexPourCode = indexService.chargerIndexBySiren(etablissementsParSiren.keySet());

        final int moyenne = calculerMoyenne(indexPourCode);
        log.info("Note moyenne : {}", moyenne);

        indexPourCode.forEach(index -> log.info("Entreprise : {} : {}", index.getRaisonSociale(), index.getNote()));

        log.info("Établissement dans le 44 non trouvés dans l'index :");

        final Set<String> sirenDansIndex = indexPourCode.stream().map(Index::getSiren).collect(toSet());

        final long nombreEtablissement44 = etablissements.stream()
                .filter(etablissement -> etablissement.getCodePostal().startsWith("44"))
                .filter(etablissement -> etablissement.getNom() != null && !etablissement.getNom().isBlank())
                .count();

        final List<Etablissement> autresEtablissements = etablissements.stream()
                .filter(etablissement -> etablissement.getCodePostal().startsWith("44"))
                .filter(etablissement -> !sirenDansIndex.contains(etablissement.getSiren()))
                .filter(etablissement -> etablissement.getNom() != null && !etablissement.getNom().isBlank())
                .toList();

        log.info("{} établissements supplémentaires dans le 44 ne sont pas dans l'index (sur {}).", autresEtablissements.size(),
                nombreEtablissement44);
        autresEtablissements.forEach(etablissement -> log.info(etablissement.getNom()));
    }

    private static int calculerMoyenne(final List<Index> index) {
        final Integer total = index.stream().map(Index::getNote).reduce(0, Integer::sum);
        return total / index.size();
    }
}
