package com.epam.cargo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static com.epam.cargo.exception.WrongInputDataKeysConstants.NO_POSITIVE_NUMBER_KEY_ERROR_MESSAGE;
import static com.epam.cargo.exception.WrongInputDataKeysConstants.REQUIRED_KEY_ERROR_MESSAGE;

@Getter
@Setter
public class DeliveryReceiptRequest {

    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    @Positive(message = NO_POSITIVE_NUMBER_KEY_ERROR_MESSAGE)
    private Double price;

}
