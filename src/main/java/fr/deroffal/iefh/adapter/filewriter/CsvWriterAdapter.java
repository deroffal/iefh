package fr.deroffal.iefh.adapter.filewriter;

import static com.opencsv.ICSVWriter.DEFAULT_ESCAPE_CHARACTER;
import static com.opencsv.ICSVWriter.DEFAULT_LINE_END;
import static com.opencsv.ICSVWriter.NO_QUOTE_CHARACTER;
import static fr.deroffal.iefh.configuration.CsvConfiguration.CSV_SEPARATOR;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.opencsv.CSVWriter;
import fr.deroffal.iefh.domain.etablissement.Etablissement;
import fr.deroffal.iefh.domain.etablissement.EtablissementCsvWriterPort;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class CsvWriterAdapter implements EtablissementCsvWriterPort {

    @Value("${fichier.etablissement.nom}")
    private Path emplacementFichier;

    @Override
    public void ecrireFichier(final List<Etablissement> etablissements) {
        final Path path = creerFichier(emplacementFichier);

        try (
            final Writer writer = Files.newBufferedWriter(path);
            final CSVWriter csvWriter = new CSVWriter(writer, CSV_SEPARATOR, NO_QUOTE_CHARACTER,DEFAULT_ESCAPE_CHARACTER, DEFAULT_LINE_END)
        ) {
            final List<String[]> lignes = etablissements.stream().map(CsvWriterAdapter::toArray).toList();
            csvWriter.writeAll(lignes);
        } catch (final IOException e) {
            log.error("Erreur lors de l'écriture du fichier.", e);
        }
    }

    @SneakyThrows
    private static Path creerFichier(final Path path) {
        Files.createDirectories(path.getParent());
        return Files.createFile(path);
    }

    private static String[] toArray(final Etablissement etablissement) {
        return new String[]{ etablissement.getNom(), etablissement.getSiret(), etablissement.getSiren(), etablissement.getActivitePrincipale(),
            etablissement.getAnneeEffectifs(), etablissement.getTrancheEffectifs(), etablissement.getCodePostal() };
    }
}
