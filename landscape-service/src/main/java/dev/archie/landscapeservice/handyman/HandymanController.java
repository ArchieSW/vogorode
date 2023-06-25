package dev.archie.landscapeservice.handyman;

import dev.archie.landscapeservice.handyman.dto.CreatingHandymanDto;
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
@RequestMapping("/handymen")
@RequiredArgsConstructor
public class HandymanController {

    private final HandymanService handymanService;

    @PostMapping
    public Handyman create(@RequestBody CreatingHandymanDto creatingHandymanDto) {
        return handymanService.create(creatingHandymanDto);
    }

    @GetMapping("/{id}")
    public Handyman getById(@PathVariable UUID id) {
        return handymanService.getById(id);
    }

    @PutMapping("/{id}")
    public Handyman update(@PathVariable UUID id, @RequestBody CreatingHandymanDto creatingHandymanDto) {
        return handymanService.update(creatingHandymanDto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        handymanService.delete(id);
    }

}
