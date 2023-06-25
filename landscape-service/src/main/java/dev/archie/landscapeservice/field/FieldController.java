package dev.archie.landscapeservice.field;

import dev.archie.landscapeservice.field.dto.CreatingFieldDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fields")
public class FieldController {

    private final FieldService fieldService;

    @PostMapping("/{gardenerId}")
    public Field create(@RequestBody CreatingFieldDto creatingFieldDto, @PathVariable UUID gardenerId) {
        return fieldService.create(creatingFieldDto, gardenerId);
    }

    @GetMapping("/{id}")
    public Field getById(@PathVariable Long id) {
        return fieldService.getById(id);
    }

    @PutMapping("/{id}")
    public Field update(@RequestBody CreatingFieldDto creatingFieldDto, @PathVariable Long id) {
        return fieldService.update(creatingFieldDto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        fieldService.delete(id);
    }

}
