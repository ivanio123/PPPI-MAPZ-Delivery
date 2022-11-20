package com.pppi.novaposhta.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.pppi.novaposhta.exception.WrongInput.*;

/**
 * Data Transfer Object to assemble data of User.<br>
 * @author group2
 * @version 1.0
 * */
@Getter
@Setter
@Builder
public class UserRequest {
    @NotNull(message = UNFILLED_NAME)
    @NotBlank(message = UNFILLED_NAME)
    private String name;

    @NotNull(message = UNFILLED_SURNAME)
    @NotBlank(message = UNFILLED_SURNAME)
    private String surname;

    @NotNull(message = NO_FILLED_LOGIN)
    @NotBlank(message = NO_FILLED_LOGIN)
    private String login;

    @NotNull(message = NO_FILLED_LOGIN)
    @NotBlank(message = NO_FILLED_PASSWORD)
    private String password;

    @NotNull(message = NO_FILLED_LOGIN)
    @NotBlank(message = MISSING_DUPLICATE_PASSWORD)
    private String duplicatePassword;

    @NotNull(message = UNFILLED_PHONE)
    @NotBlank(message = UNFILLED_PHONE)
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
