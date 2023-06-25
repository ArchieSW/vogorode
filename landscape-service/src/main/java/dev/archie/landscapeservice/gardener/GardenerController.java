package dev.archie.landscapeservice.gardener;

import dev.archie.landscapeservice.gardener.dto.CreatingGardenerDto;
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
@RequestMapping("/gardeners")
@RequiredArgsConstructor
public class GardenerController {

    private final GardenerService gardenerService;

    @PostMapping
    public Gardener create(@RequestBody CreatingGardenerDto creatingGardenerDto) {
        return gardenerService.create(creatingGardenerDto);
    }

    @GetMapping("/{id}")
    public Gardener getById(@PathVariable UUID id) {
        return gardenerService.getById(id);
    }

    @PutMapping("/{id}")
    public Gardener update(@PathVariable UUID id,
                           @RequestBody CreatingGardenerDto creatingGardenerDto) {
        return gardenerService.update(creatingGardenerDto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        gardenerService.delete(id);
    }

}
