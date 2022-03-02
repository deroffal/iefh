package fr.deroffal.iefh.adapter.repository.index;

import fr.deroffal.iefh.configuration.MapperConfiguration;
import fr.deroffal.iefh.domain.index.Index;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
interface IndexCsvMapper {

    Index asIndex(IndexCsv indexCsv);

    default Integer convertirNote(final String note) {
        try {
            return Integer.parseInt(note);
        } catch (final NumberFormatException e) {
            return null;
        }
    }
}
