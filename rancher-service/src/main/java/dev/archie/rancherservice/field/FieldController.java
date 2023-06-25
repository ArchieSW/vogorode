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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fields")
@RequiredArgsConstructor
public class FieldController {

    private final FieldService fieldService;

    @PostMapping("/{gardenerId}")
    public Field create(@RequestBody CreatingFieldDto creatingFieldDto,
                        @PathVariable String gardenerId) {
        return fieldService.create(creatingFieldDto, gardenerId);
    }

    @GetMapping("/{fieldId}")
    public Field getById(@PathVariable String fieldId) {
        return fieldService.getById(fieldId);
    }

    @PutMapping("/{fieldId}")
    public Field update(@RequestBody CreatingFieldDto creatingFieldDto, @PathVariable String fieldId) {
        return fieldService.update(creatingFieldDto, fieldId);
    }

    @DeleteMapping("/{fieldId}")
    public void delete(@PathVariable String fieldId) {
        fieldService.delete(fieldId);
    }

}
