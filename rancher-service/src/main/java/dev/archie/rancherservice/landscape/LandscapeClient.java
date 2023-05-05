package dev.archie.rancherservice.landscape;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(value = "user-service", url = "${application.landscape.rest.url}")
public interface LandscapeClient {

    @PostMapping("/users")
    User create(@RequestBody CreatingUserDto creatingUserDto);

    @GetMapping("/users/{id}")
    User getById(@PathVariable UUID id);

    @PutMapping("/users/{id}")
    User update(@PathVariable UUID id, @RequestBody CreatingUserDto creatingUserDto);

    @DeleteMapping("/users/{id}")
    void delete(@PathVariable UUID id);

}