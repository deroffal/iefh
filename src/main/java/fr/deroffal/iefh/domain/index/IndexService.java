package fr.deroffal.iefh.domain.index;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndexService {

    private final IndexRepositoryPort indexRepository;

    public List<Index> chargerIndexPourEtablissementDansSiren(final Collection<String> sirens) {
        return indexRepository.findAllBySirenAndNoteNotNull(sirens);
    }
}
