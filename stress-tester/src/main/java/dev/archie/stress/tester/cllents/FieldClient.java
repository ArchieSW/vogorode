package dev.archie.stress.tester.cllents;

import dev.archie.stress.tester.dto.CreatingFieldDto;
import dev.archie.stress.tester.entities.Field;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "field-service", url = "${rancher-service-url}")
public interface FieldClient {

    @PostMapping("/fields")
    Field create(@RequestBody CreatingFieldDto fieldDto, @RequestParam Long gardenerId);

    @GetMapping("/fields/{id}")
    Field getById(@PathVariable Long id);

    @PutMapping("/fields/{id}")
    Field update(@RequestBody CreatingFieldDto fieldDto, @PathVariable Long id);

    @DeleteMapping("/fields/{id}")
    void delete(@PathVariable Long id);
}
