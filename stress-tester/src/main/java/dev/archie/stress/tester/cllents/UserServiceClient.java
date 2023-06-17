package dev.archie.stress.tester.cllents;

import dev.archie.stress.tester.dto.CreatingUserDto;
import dev.archie.stress.tester.entities.Page;
import dev.archie.stress.tester.entities.User;
import dev.archie.stress.tester.entities.UserType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "user-service", url = "${landscape-service-url}")
public interface UserServiceClient {

    @PostMapping("/users")
    User createUser(@RequestBody CreatingUserDto creatingUserDto);

    @GetMapping("/users/{id}")
    User getUserById(@PathVariable UUID id);

    @PutMapping("/users/{id}")
    User updateUser(@PathVariable UUID id, @RequestBody CreatingUserDto creatingUserDto);

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable UUID id);

    @GetMapping("/users/types")
    List<UserType> getUserTypes();

    @GetMapping("/users/handymen")
    Page<User> getAllHandymen(@RequestParam(name = "size") int pageSize,
                              @RequestParam(name = "number") int pageNumber);

    @GetMapping("/users/ranchers")
    Page<User> getAllRanchers(@RequestParam(name = "size") int pageSize,
                              @RequestParam(name = "number") int pageNumber);

}

