package dev.archie.handymanservice.user;

import dev.archie.handymanservice.user.dto.CreatingHandymanUserDto;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class HandymanUserController {

    private final HandymanUserService userService;

    @PostMapping
    public HandymanUser create(@RequestBody CreatingHandymanUserDto handymanUserDto) throws IOException {
        return userService.create(handymanUserDto);
    }

    @PostMapping("/photo/{id}")
    public HandymanUser publishPhoto(@RequestParam MultipartFile photo, @PathVariable Long id) throws IOException {
        return userService.publishPhoto(id, photo);
    }

    @GetMapping("/{id}")
    public HandymanUser getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PutMapping("/{id}")
    public HandymanUser update(@RequestBody CreatingHandymanUserDto handymanUserDto, @PathVariable Long id) {
        return userService.update(handymanUserDto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping
    public Page<HandymanUser> getAll(@RequestParam(name = "size") int pageSize,
                                     @RequestParam(name = "number") int pageNumber) {
        return userService.getAll(pageSize, pageNumber);
    }
}
