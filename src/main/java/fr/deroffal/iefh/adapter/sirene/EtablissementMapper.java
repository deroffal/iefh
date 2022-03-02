package fr.deroffal.iefh.adapter.sirene;

import fr.deroffal.iefh.configuration.MapperConfiguration;
import fr.deroffal.iefh.domain.etablissement.Etablissement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class)
interface EtablissementMapper {

    @Mapping(target = "nom", source = "uniteLegale.denomination")
    Etablissement asEtablissement(EtablissementSirene etablissementSirene);
}
