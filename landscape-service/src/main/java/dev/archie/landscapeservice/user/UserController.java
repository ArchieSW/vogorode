package dev.archie.landscapeservice.user;

import dev.archie.landscapeservice.user.dto.CreatingUserDto;
import dev.archie.landscapeservice.user.type.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * @param creatingUserDto user to create
     * @return created user
     */
    @PostMapping
    public User create(@RequestBody CreatingUserDto creatingUserDto) {
        return userService.create(creatingUserDto);
    }

    /**
     * @param id of existing user
     * @return found user
     */
    @GetMapping("/{id}")
    public User getById(@PathVariable UUID id) {
        return userService.getById(id);
    }

    /**
     * @param id              of existing user
     * @param creatingUserDto new fields for user. New email should not exist
     * @return updated user
     */
    @PutMapping("/{id}")
    public User update(@PathVariable UUID id, @RequestBody CreatingUserDto creatingUserDto) {
        return userService.update(id, creatingUserDto);
    }

    /**
     * @param id of existing user to delete
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userService.delete(id);
    }

    /**
     * @return available user types
     */
    @GetMapping("/types")
    public List<UserType> getUserTypes() {
        return userService.getUserTypes();
    }

    /**
     * @return pageable handyman users
     */
    @GetMapping("/handymen")
    public Page<User> getAllHandymen(@RequestParam(name = "size") int pageSize,
                                     @RequestParam(name = "number") int pageNumber) {
        return userService.getAllHandymen(pageSize, pageNumber);
    }

    /**
     * @return pageable ranchers users
     */
    @GetMapping("/ranchers")
    public Page<User> getAllRanchers(@RequestParam(name = "size") int pageSize,
                                     @RequestParam(name = "number") int pageNumber) {
        return userService.getAllRanchers(pageSize, pageNumber);
    }
}
