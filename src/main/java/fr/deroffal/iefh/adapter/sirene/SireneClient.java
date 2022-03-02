package fr.deroffal.iefh.adapter.sirene;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sireneClient", url = "${sirene.baseUrl}")
public interface SireneClient {

    @GetMapping("/api/sirene/v3/etablissements/")
    RetourRecheche listerEtablissementActifPourCodeNaf(@RequestParam(name = "activite_principale") String activitePrincipale,
        @RequestParam(name = "etat_administratif") String etatAdministratif, @RequestParam int page, @RequestParam("per_page") int perPage);
}
