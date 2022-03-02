package fr.deroffal.iefh.adapter.repository.index;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class IndexCsv {

    @CsvBindByPosition(position = 0) private String raisonSociale;
    @CsvBindByPosition(position = 1) private String siren;
    @CsvBindByPosition(position = 2) private String annee;
    @CsvBindByPosition(position = 3) private String note;
    @CsvBindByPosition(position = 4) private String structure;
    @CsvBindByPosition(position = 5) private String nomUES;
    @CsvBindByPosition(position = 6) private String entrepriseUES;
    @CsvBindByPosition(position = 7) private String region;
    @CsvBindByPosition(position = 8) private String departement;
}
