package dev.archie.rancherservice.rancher;

import dev.archie.rancherservice.rancher.dto.CreatingProfileDto;
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
@RequiredArgsConstructor
@RequestMapping("/ranchers")
public class RancherController {

    private final RancherService rancherService;

    /**
     * @param creatingProfileDto rancher to create
     * @return created rancher's profile
     */
    @PostMapping
    public Profile create(@RequestBody CreatingProfileDto creatingProfileDto) {
        return rancherService.create(creatingProfileDto);
    }

    /**
     * @param id of existing rancher
     * @return found profile
     */
    @GetMapping("/{id}")
    public Profile getById(@PathVariable String id) {
        return rancherService.getProfileById(id);
    }

    /**
     * @param id of existing rancher
     * @param creatingProfileDto new fields. New email should not exist
     * @return updated profile
     */
    @PutMapping("/{id}")
    public Profile update(@PathVariable String id, @RequestBody CreatingProfileDto creatingProfileDto) {
        return rancherService.update(id, creatingProfileDto);
    }

    /**
     * @param id of existing user
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        rancherService.delete(id);
    }

}
