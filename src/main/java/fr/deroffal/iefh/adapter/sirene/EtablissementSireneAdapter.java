package fr.deroffal.iefh.adapter.sirene;

import java.util.ArrayList;
import java.util.List;

import fr.deroffal.iefh.domain.etablissement.Etablissement;
import fr.deroffal.iefh.domain.etablissement.EtablissementSirenePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class EtablissementSireneAdapter implements EtablissementSirenePort {

    private static final String ETAT_ACTIF = "A";

    private final SireneClient sireneClient;
    private final EtablissementMapper etablissementMapper;

    @Value("${sirene.nombreResultatParPage}")
    private int nombreResultatParPage;

    @Override
    public List<Etablissement> listerEtablissementActifPourCodeNaf(final String codeNaf) {
        int page = 1;

        final RetourRecheche retourRecheche = sireneClient.listerEtablissementActifPourCodeNaf(codeNaf, ETAT_ACTIF, page, nombreResultatParPage);
        final List<EtablissementSirene> etablissements = new ArrayList<>(retourRecheche.getEtablissements());
        page += 1;
        log.info("Page : {} / Établissements récupérés : {}", page, etablissements.size());

        int totalPage = retourRecheche.getMeta().getTotalPages();

        while (page <= totalPage) {
            final RetourRecheche retour = sireneClient.listerEtablissementActifPourCodeNaf(codeNaf, ETAT_ACTIF, page, nombreResultatParPage);
            etablissements.addAll(retour.getEtablissements());
            page += 1;
            log.info("Page : {} / Établissements récupérés : {}", page, etablissements.size());
        }

        return etablissements.stream().map(etablissementMapper::asEtablissement).toList();
    }
}
