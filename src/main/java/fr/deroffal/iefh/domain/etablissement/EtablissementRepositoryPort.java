package fr.deroffal.iefh.domain.etablissement;

import java.util.List;

public interface EtablissementRepositoryPort {

    List<Etablissement> findAll();
}
