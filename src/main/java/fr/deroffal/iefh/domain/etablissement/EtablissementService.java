package fr.deroffal.iefh.domain.etablissement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EtablissementService {

    private static final String CODE_NAF = "62.02A";

    private final EtablissementRepositoryPort etablissementRepository;

    private final EtablissementSirenePort etablissementSirenePort;
    private final EtablissementCsvWriterPort etablissementCsvWriterPort;

    public List<Etablissement> chargerEtablissements() {
        List<Etablissement> etablissements = etablissementRepository.findAll();
        if (etablissements.isEmpty()) {
            log.info("Pas de fichier csv pour les établissements, récupération via l'API.");
            etablissements = etablissementSirenePort.listerEtablissementActifPourCodeNaf(CODE_NAF);
            etablissementCsvWriterPort.ecrireFichierCsvEtablissements(etablissements);
        }
        return etablissements;
    }
}
