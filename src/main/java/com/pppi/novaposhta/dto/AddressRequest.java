package com.pppi.novaposhta.dto;

import com.pppi.novaposhta.exception.WrongInputDataKeysConstants;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressRequest {

    @NotNull(message = WrongInputDataKeysConstants.REQUIRED_KEY_ERROR_MESSAGE)
    private Long cityId;

    @NotNull(message = WrongInputDataKeysConstants.REQUIRED_KEY_ERROR_MESSAGE)
    @NotBlank(message = WrongInputDataKeysConstants.REQUIRED_KEY_ERROR_MESSAGE)
    private String streetName;

    @NotNull(message = WrongInputDataKeysConstants.REQUIRED_KEY_ERROR_MESSAGE)
    @NotBlank(message = WrongInputDataKeysConstants.REQUIRED_KEY_ERROR_MESSAGE)
    private String houseNumber;
}
