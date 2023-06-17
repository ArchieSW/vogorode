package dev.archie.stress.tester.cllents;

import dev.archie.stress.tester.dto.CreatingHandymanUserDto;
import dev.archie.stress.tester.entities.HandymanUser;
import dev.archie.stress.tester.entities.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "handyman-user-client", url = "${handyman-service-url}", path = "/users")
public interface HandymanUserClient {

    @PostMapping
    HandymanUser create(@RequestBody CreatingHandymanUserDto handymanUserDto);

    @PostMapping("/photo/{id}")
    HandymanUser publishPhoto(@RequestParam MultipartFile photo, @PathVariable Long id);

    @GetMapping("/{id}")
    HandymanUser getById(@PathVariable Long id);

    @PutMapping("/{id}")
    HandymanUser update(@RequestBody CreatingHandymanUserDto handymanUserDto, @PathVariable Long id);

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);


    @GetMapping
    Page<HandymanUser> getAll(@RequestParam(name = "size") int pageSize,
                              @RequestParam(name = "number") int pageNumber);
}
