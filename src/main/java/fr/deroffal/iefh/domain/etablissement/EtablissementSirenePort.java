package fr.deroffal.iefh.domain.etablissement;

import java.util.List;

public interface EtablissementSirenePort {

    List<Etablissement> listerEtablissementActifPourCodeNaf(String codeNaf);
}
