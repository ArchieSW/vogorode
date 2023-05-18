package dev.archie.rancherservice.gardener;

import dev.archie.rancherservice.gardener.dto.CreatingGardenerDto;
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
@RequestMapping("gardeners")
@RequiredArgsConstructor
public class GardenerController {

    private final GardenerService gardenerService;

    @PostMapping
    public Gardener create(@RequestBody CreatingGardenerDto gardenerDto) {
        return gardenerService.create(gardenerDto);
    }

    @GetMapping("/{id}")
    public Gardener getById(@PathVariable Long id) {
        return gardenerService.getById(id);
    }

    @PutMapping("/{id}")
    public Gardener update(@PathVariable Long id, @RequestBody CreatingGardenerDto gardenerDto) {
        return gardenerService.update(id, gardenerDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        gardenerService.deleteById(id);
    }

}
