package fr.deroffal.iefh.adapter.filewriter;

import com.opencsv.CSVWriter;
import fr.deroffal.iefh.domain.etablissement.Etablissement;
import fr.deroffal.iefh.domain.etablissement.EtablissementCsvWriterPort;
import fr.deroffal.iefh.domain.index.Index;
import fr.deroffal.iefh.domain.reporting.ReportingCsvWriterPort;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.opencsv.ICSVWriter.*;
import static fr.deroffal.iefh.configuration.CsvConfiguration.CSV_SEPARATOR;

@Slf4j
@Component
class CsvWriterAdapter implements EtablissementCsvWriterPort, ReportingCsvWriterPort {

    @Value("${fichier.entree.etablissement.nom}")
    private Path emplacementFichierEtablissement;

    @Value("${fichier.sortie.notes}")
    private Path emplacementFichierNotes;

    @Value("${fichier.sortie.autresEtablissements}")
    private String emplacementFichierAutresEtablissements;

    @Override
    public void ecrireFichierCsvEtablissements(final List<Etablissement> etablissements) {
        final Path emplacementFichier = creerFichier(emplacementFichierEtablissement);
        try (
                final Writer writer = Files.newBufferedWriter(emplacementFichier);
                final CSVWriter csvWriter = new CSVWriter(writer, CSV_SEPARATOR, NO_QUOTE_CHARACTER, DEFAULT_ESCAPE_CHARACTER, DEFAULT_LINE_END)
        ) {
            final List<String[]> lignes = etablissements.stream().map(CsvWriterAdapter::extraireDonneesEtablissement).toList();
            csvWriter.writeAll(lignes);
        } catch (final IOException e) {
            log.error("Erreur lors de l'écriture du fichier {}.", emplacementFichierEtablissement, e);
        }
    }

    @SneakyThrows
    private static Path creerFichier(final Path path) {
        Files.createDirectories(path.getParent());
        return Files.createFile(path);
    }

    private static String[] extraireDonneesEtablissement(final Etablissement etablissement) {
        return new String[]{etablissement.getNom(), etablissement.getSiret(), etablissement.getSiren(), etablissement.getActivitePrincipale(),
                etablissement.getAnneeEffectifs(), etablissement.getTrancheEffectifs(), etablissement.getCodePostal()};
    }

    @Override
    public void ecrireFichierCsvNotes(final List<Index> index) {
        final Path emplacementFichier = creerFichier(emplacementFichierNotes);
        try (
                final Writer writer = Files.newBufferedWriter(emplacementFichier);
                final CSVWriter csvWriter = new CSVWriter(writer, CSV_SEPARATOR, NO_QUOTE_CHARACTER, DEFAULT_ESCAPE_CHARACTER, DEFAULT_LINE_END)
        ) {
            final List<String[]> lignes = index.stream().map(CsvWriterAdapter::extraireDonneesNotesDeIndex).toList();
            csvWriter.writeAll(lignes);
        } catch (final IOException e) {
            log.error("Erreur lors de l'écriture du fichier {}.", emplacementFichierEtablissement, e);
        }
    }

    private static String[] extraireDonneesNotesDeIndex(final Index index) {
        return new String[]{index.getRaisonSociale(), index.getNote().toString()};
    }

    @Override
    public void creerFichierAutresEtablissementsDuDepartement(final String numeroDepartement, final List<Etablissement> autresEtablissements) {
        final String emplacement = String.format(emplacementFichierAutresEtablissements, numeroDepartement);
        final Path emplacementFichier = creerFichier(Path.of(emplacement));
        try (
                final Writer writer = Files.newBufferedWriter(emplacementFichier);
                final CSVWriter csvWriter = new CSVWriter(writer, CSV_SEPARATOR, NO_QUOTE_CHARACTER, DEFAULT_ESCAPE_CHARACTER, DEFAULT_LINE_END)
        ) {
            final List<String[]> lignes = autresEtablissements.stream().map(CsvWriterAdapter::extraireDonneesEtablissementAbsents).toList();
            csvWriter.writeAll(lignes);
        } catch (final IOException e) {
            log.error("Erreur lors de l'écriture du fichier {}.", emplacementFichierAutresEtablissements, e);
        }
    }

    private static String[] extraireDonneesEtablissementAbsents(final Etablissement etablissement) {
        return new String[]{etablissement.getNom(), etablissement.getSiren()};
    }
}
