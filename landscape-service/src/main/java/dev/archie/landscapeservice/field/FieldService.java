package dev.archie.landscapeservice.field;

import dev.archie.landscapeservice.field.exceptions.FieldDoesNotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FieldService {

    private final FieldRepository fieldRepository;

    public Field getById(Long id) {
        return fieldRepository.findById(id)
                .orElseThrow(() -> new FieldDoesNotExistsException(id));
    }

}
