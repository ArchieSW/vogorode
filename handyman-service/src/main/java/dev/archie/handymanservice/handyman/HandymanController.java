package dev.archie.handymanservice.handyman;

import dev.archie.handymanservice.handyman.dto.CreatingHandymanDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/handymen")
public class HandymanController {

    private final HandymanService handymanService;

    /**
     * @param creatingHandymanDto handyman dto create. Email should not exist
     * @return Created profile
     */
    @PostMapping
    public Profile create(@RequestBody CreatingHandymanDto creatingHandymanDto) {
        return handymanService.create(creatingHandymanDto);
    }

    /**
     * @param id for existing profile
     * @return found profile
     */
    @GetMapping("/{id}")
    public Profile getById(@PathVariable String id) {
        return handymanService.getProfileById(id);
    }

    /**
     * @return existing handyman profiles
     */
    @GetMapping
    public List<Handyman> getAll() {
        return handymanService.getAll();
    }

    /**
     * @param id                  of existing profile
     * @param creatingHandymanDto of new fields. New email should not exist
     * @return Profile od updated handyman
     */
    @PutMapping("/{id}")
    public Profile update(@PathVariable String id, @RequestBody CreatingHandymanDto creatingHandymanDto) {
        return handymanService.update(id, creatingHandymanDto);
    }

    /**
     * @param id of existing handyman
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        handymanService.delete(id);
    }
}