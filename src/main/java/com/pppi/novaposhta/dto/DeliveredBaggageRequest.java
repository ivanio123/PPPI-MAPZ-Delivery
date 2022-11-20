package com.pppi.novaposhta.dto;

import com.pppi.novaposhta.entity.BaggageType;
import com.pppi.novaposhta.exception.WrongInputDataKeysConstants;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class DeliveredBaggageRequest {

    @NotNull(message = WrongInputDataKeysConstants.REQUIRED_KEY_ERROR_MESSAGE)
    @Positive(message = WrongInputDataKeysConstants.NO_POSITIVE_NUMBER_KEY_ERROR_MESSAGE)
    private Double weight;

    @NotNull(message = WrongInputDataKeysConstants.REQUIRED_KEY_ERROR_MESSAGE)
    @Positive(message = WrongInputDataKeysConstants.NO_POSITIVE_NUMBER_KEY_ERROR_MESSAGE)
    private Double volume;

    @NotNull(message = WrongInputDataKeysConstants.REQUIRED_KEY_ERROR_MESSAGE)
    private BaggageType type;

    private String description;

}
