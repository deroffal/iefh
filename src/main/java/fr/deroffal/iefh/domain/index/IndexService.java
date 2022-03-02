package fr.deroffal.iefh.domain.index;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IndexService {

    private final IndexRepositoryPort indexRepository;

    public List<Index> chargerIndexBySiren(final Collection<String> sirens) {
        return indexRepository.findAllBySirenAndNoteNotNull(sirens).stream().filter(index -> index.getNote() != null).toList();
    }
}
