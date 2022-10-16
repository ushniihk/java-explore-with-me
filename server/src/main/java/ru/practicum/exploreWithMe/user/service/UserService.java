package ru.practicum.exploreWithMe.user.service;

import ru.practicum.exploreWithMe.user.model.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll(int from, int size);

    UserDto get(long userId);

    UserDto add(UserDto userDto);

    void delete(long userId);

    UserDto update(long userId, UserDto userDto);
}
