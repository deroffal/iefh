package fr.deroffal.iefh.adapter.repository.etablissement;

import fr.deroffal.iefh.configuration.MapperConfiguration;
import fr.deroffal.iefh.domain.etablissement.Etablissement;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
interface EtablissementCsvMapper {

    Etablissement asEtablissement(EtablissementCsv etablissementCsv);
}
