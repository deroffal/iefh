package fr.deroffal.iefh.adapter.repository.etablissement;

import static fr.deroffal.iefh.configuration.CsvConfiguration.CSV_SEPARATOR;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.List;

import com.opencsv.bean.CsvToBeanBuilder;
import fr.deroffal.iefh.domain.etablissement.Etablissement;
import fr.deroffal.iefh.domain.etablissement.EtablissementRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class EtablissementCsvLoader implements EtablissementRepositoryPort {

    @Value("${fichier.etablissement.nom}")
    private Path emplacementFichier;

    private final EtablissementCsvMapper etablissementCsvMapper;

    @Override
    public List<Etablissement> findAll() {
        try {
            final List<Object> lignesCsv = new CsvToBeanBuilder<>(new FileReader(emplacementFichier.toFile()))
                .withType(EtablissementCsv.class)
                .withSeparator(CSV_SEPARATOR)
                .build().parse();
            return lignesCsv.stream().map(EtablissementCsv.class::cast).map(etablissementCsvMapper::asEtablissement).toList();
        } catch (final FileNotFoundException e) {
            return List.of();
        }
    }
}
