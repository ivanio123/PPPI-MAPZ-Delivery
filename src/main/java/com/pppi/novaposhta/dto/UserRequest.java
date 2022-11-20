package com.pppi.novaposhta.dto;

import com.pppi.novaposhta.exception.WrongInputDataKeysConstants;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class UserRequest {
    private String name;
    private String surname;

    @NotNull(message = WrongInputDataKeysConstants.NO_FILLED_LOGIN_KEY_ERROR_MESSAGE)
    @NotBlank(message = WrongInputDataKeysConstants.NO_FILLED_LOGIN_KEY_ERROR_MESSAGE)
    private String login;

    @NotNull(message = WrongInputDataKeysConstants.NO_FILLED_LOGIN_KEY_ERROR_MESSAGE)
    @NotBlank(message = WrongInputDataKeysConstants.NO_FILLED_PASSWORD_KEY_ERROR_MESSAGE)
    private String password;

    @NotNull(message = WrongInputDataKeysConstants.NO_FILLED_LOGIN_KEY_ERROR_MESSAGE)
    @NotBlank(message = WrongInputDataKeysConstants.NO_FILLED_DUPLICATE_PASSWORD_KEY_ERROR_MESSAGE)
    private String duplicatePassword;

    private String phone;
    private String email;

    private AddressRequest address;

    public UserRequest() {
    }

    public UserRequest(String name, String surname, String login, String password, String duplicatePassword, String phone, String email, AddressRequest address) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.duplicatePassword = duplicatePassword;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }
}
