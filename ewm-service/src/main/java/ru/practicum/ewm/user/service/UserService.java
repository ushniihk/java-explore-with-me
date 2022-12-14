package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll(List<Long> ids, int from, int size);

    UserDto get(long userId);

    UserDto add(UserDto userDto);

    void delete(long userId);

}
