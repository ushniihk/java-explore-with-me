package ru.practicum.ewm.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
public class UserDto {

    private long id;
    private String name;
    @Email
    private String email;

}

