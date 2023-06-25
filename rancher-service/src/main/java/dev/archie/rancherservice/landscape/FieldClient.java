package dev.archie.rancherservice.landscape;

import dev.archie.rancherservice.field.dto.CreatingFieldDto;
import dev.archie.rancherservice.landscape.dto.FieldDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(path = "/fields", url = "${application.landscape.rest.url}", name = "field-client")
public interface FieldClient {

    @PostMapping("/{gardenerId}")
    FieldDto create(@RequestBody CreatingFieldDto creatingFieldDto, @PathVariable UUID gardenerId);

    @GetMapping("/{id}")
    FieldDto getById(@PathVariable Long id);

    @PutMapping("/{id}")
    FieldDto update(@RequestBody CreatingFieldDto creatingFieldDto, @PathVariable Long id);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);

}
