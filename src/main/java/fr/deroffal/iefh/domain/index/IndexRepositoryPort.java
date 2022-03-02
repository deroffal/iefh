package fr.deroffal.iefh.domain.index;

import java.util.Collection;
import java.util.List;

public interface IndexRepositoryPort {

    List<Index> findAllBySirenAndNoteNotNull(Collection<String> sirens);
}
