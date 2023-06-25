package dev.archie.rancherservice.landscape;

import dev.archie.rancherservice.landscape.dto.GardenerDto;
import dev.archie.rancherservice.rancher.dto.CreatingGardenerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "gardener-client", url = "${application.landscape.rest.url}", path = "gardeners")
public interface GardenerClient {

    @PostMapping
    GardenerDto create(@RequestBody CreatingGardenerDto creatingGardenerDto);

    @GetMapping("/{id}")
    GardenerDto getById(@PathVariable UUID id);

    @PutMapping("/{id}")
    GardenerDto update(@PathVariable UUID id,
                    @RequestBody CreatingGardenerDto creatingGardenerDto);

    @DeleteMapping("/{id}")
    void delete(@PathVariable UUID id);

}
