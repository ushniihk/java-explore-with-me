package ru.practicum.exploreWithMe.user.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.user.model.UserDto;
import ru.practicum.exploreWithMe.user.service.UserService;

import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
@Data
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getAll(@RequestParam(required = false, defaultValue = "0") int from,
                                @RequestParam(required = false, defaultValue = "10") int size) {
        return userService.getAll(from, size);
    }

    @PostMapping
    public UserDto add(@RequestBody UserDto userDto) {
        return userService.add(userDto);
    }

    @DeleteMapping("/{userId}")
    public void update(@PathVariable long userId) {
        userService.delete(userId);
    }
}
