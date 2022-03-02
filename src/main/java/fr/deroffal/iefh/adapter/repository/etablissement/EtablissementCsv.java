package fr.deroffal.iefh.adapter.repository.etablissement;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
public class EtablissementCsv {

       @CsvBindByPosition(position = 0) private String nom;
       @CsvBindByPosition(position = 1) private String siret;
       @CsvBindByPosition(position = 2) private String siren;
       @CsvBindByPosition(position = 3) private String activitePrincipale;
       @CsvBindByPosition(position = 4) private String anneeEffectifs;
       @CsvBindByPosition(position = 5) private String trancheEffectifs;
       @CsvBindByPosition(position = 6) private String codePostal;
}
