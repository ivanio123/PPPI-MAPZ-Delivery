package com.epam.cargo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.epam.cargo.exception.WrongInputDataKeysConstants.*;

@Getter
@Setter
public class UserRequest {
    private String name;
    private String surname;

    @NotNull(message = NO_FILLED_LOGIN_KEY_ERROR_MESSAGE)
    @NotBlank(message = NO_FILLED_LOGIN_KEY_ERROR_MESSAGE)
    private String login;

    @NotNull(message = NO_FILLED_LOGIN_KEY_ERROR_MESSAGE)
    @NotBlank(message = NO_FILLED_PASSWORD_KEY_ERROR_MESSAGE)
    private String password;

    @NotNull(message = NO_FILLED_LOGIN_KEY_ERROR_MESSAGE)
    @NotBlank(message = NO_FILLED_DUPLICATE_PASSWORD_KEY_ERROR_MESSAGE)
    private String duplicatePassword;

    private String phone;
    private String email;

    private AddressRequest address;

}
