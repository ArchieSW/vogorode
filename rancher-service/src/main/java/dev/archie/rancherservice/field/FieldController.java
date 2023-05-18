package dev.archie.rancherservice.field;

import dev.archie.rancherservice.field.dto.CreatingFieldDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fields")
@RequiredArgsConstructor
public class FieldController {

    private final FieldService fieldService;

    @PostMapping
    public Field create(@RequestBody CreatingFieldDto fieldDto, @RequestParam Long gardenerId) {
        return fieldService.create(fieldDto, gardenerId);
    }

    @GetMapping("/{id}")
    public Field getById(@PathVariable Long id) {
        return fieldService.getById(id);
    }

    @PutMapping("/{id}")
    public Field update(@RequestBody CreatingFieldDto fieldDto, @PathVariable Long id) {
        return fieldService.update(fieldDto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        fieldService.delete(id);
    }

}
