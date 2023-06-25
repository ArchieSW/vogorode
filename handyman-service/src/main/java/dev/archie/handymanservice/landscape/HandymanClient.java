package dev.archie.handymanservice.landscape;

import dev.archie.handymanservice.handyman.dto.CreatingHandymanDto;
import dev.archie.handymanservice.landscape.dto.HandymanDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "handyman-client", path = "/handymen", url = "${application.landscape.rest.url}")
public interface HandymanClient {

    @PostMapping
    HandymanDto create(@RequestBody CreatingHandymanDto creatingHandymanDto);

    @GetMapping("/{id}")
    HandymanDto getById(@PathVariable UUID id);

    @PutMapping("/{id}")
    HandymanDto update(@PathVariable UUID id, @RequestBody CreatingHandymanDto creatingHandymanDto);

    @DeleteMapping("/{id}")
    void delete(@PathVariable UUID id);

}
