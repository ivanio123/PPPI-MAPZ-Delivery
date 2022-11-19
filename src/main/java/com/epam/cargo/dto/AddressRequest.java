package com.epam.cargo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.epam.cargo.exception.WrongInputDataKeysConstants.REQUIRED_KEY_ERROR_MESSAGE;

@Getter
@Setter
public class AddressRequest {

    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    private Long cityId;

    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    @NotBlank(message = REQUIRED_KEY_ERROR_MESSAGE)
    private String streetName;

    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    @NotBlank(message = REQUIRED_KEY_ERROR_MESSAGE)
    private String houseNumber;
}
