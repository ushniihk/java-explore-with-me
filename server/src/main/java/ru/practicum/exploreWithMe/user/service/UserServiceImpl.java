package ru.practicum.exploreWithMe.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.exploreWithMe.exceptions.CreatingException;
import ru.practicum.exploreWithMe.exceptions.NotFoundParameterException;
import ru.practicum.exploreWithMe.user.dto.UserDto;
import ru.practicum.exploreWithMe.user.dto.UserMapper;
import ru.practicum.exploreWithMe.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAll(List<Long> ids, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size, Sort.by("id").ascending());
        return userRepository.findAllByIds(ids, pageRequest).stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto get(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundParameterException("bad id");
        }
        return UserMapper.toUserDto(userRepository.getReferenceById(userId));
    }

    @Override
    public UserDto add(UserDto userDto) {
        checkEmail(userDto);
        userRepository.save(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(userRepository.getUserByEmail(userDto.getEmail()));
    }

    @Override
    public void delete(long userId) {
        userRepository.deleteById(userId);
    }

    private void checkEmail(UserDto userDto) {
        if ((userDto.getEmail() == null) || (!userDto.getEmail().contains("@"))) {
            throw new CreatingException("bad email");

        }
    }
}
