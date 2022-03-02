package fr.deroffal.iefh.adapter.sirene;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Metadonnees {

    @JsonProperty("total_results")
    private int totalResults;
    @JsonProperty("per_page")
    private int perPage;
    @JsonProperty("total_pages")
    private int totalPages;
    private int page;
}
