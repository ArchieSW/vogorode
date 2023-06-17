package dev.archie.stress.tester.cllents;

import dev.archie.stress.tester.dto.CreatingGardenerDto;
import dev.archie.stress.tester.entities.Gardener;
import dev.archie.stress.tester.entities.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gardener-service", url = "${rancher-service-url}")
public interface GardenerServiceClient {

    @PostMapping("/gardeners")
    Gardener createGardener(@RequestBody CreatingGardenerDto gardenerDto);

    @GetMapping("/gardeners/{id}")
    Gardener getGardenerById(@PathVariable Long id);

    @PutMapping("/gardeners/{id}")
    Gardener updateGardener(@PathVariable Long id, @RequestBody CreatingGardenerDto gardenerDto);

    @DeleteMapping("/gardeners/{id}")
    void deleteGardener(@PathVariable Long id);


    @GetMapping("/gardeners")
    Page<Gardener> getAll(@RequestParam(name = "size") int pageSize,
                                 @RequestParam(name = "number") int pageNumber);
}
