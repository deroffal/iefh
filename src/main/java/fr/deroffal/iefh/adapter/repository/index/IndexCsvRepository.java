package fr.deroffal.iefh.adapter.repository.index;

import com.opencsv.bean.CsvToBeanBuilder;
import fr.deroffal.iefh.domain.index.Index;
import fr.deroffal.iefh.domain.index.IndexRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static fr.deroffal.iefh.configuration.CsvConfiguration.CSV_SEPARATOR;

@Repository
@RequiredArgsConstructor
class IndexCsvRepository implements IndexRepositoryPort {

    @Value("${fichier.index.nom}")
    private String nomFichier;

    private final IndexCsvMapper indexCsvMapper;

    @Override
    public List<Index> findAllBySirenAndNoteNotNull(final Collection<String> sirens) {
        final Stream<IndexCsv> lignesCsv = lireCsv(IndexCsv.class);
        return lignesCsv.map(indexCsvMapper::asIndex)
                .filter(index -> sirens.contains(index.getSiren()))
                .filter(index -> index.getNote() != null)
                .toList();

    }

    private <T> Stream<T> lireCsv(final Class<T> classeCible) {
        try {
            final List<Object> lignes = new CsvToBeanBuilder<>(new FileReader(nomFichier)).withType(IndexCsv.class)
                    .withSkipLines(1)
                    .withSeparator(CSV_SEPARATOR)
                    .build().parse();
            return lignes.stream().map(classeCible::cast);
        } catch (final FileNotFoundException e) {
            return Stream.of();
        }
    }
}
