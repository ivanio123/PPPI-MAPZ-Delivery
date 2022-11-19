package com.epam.cargo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static com.epam.cargo.exception.WrongInputDataKeysConstants.NO_POSITIVE_NUMBER_KEY_ERROR_MESSAGE;
import static com.epam.cargo.exception.WrongInputDataKeysConstants.REQUIRED_KEY_ERROR_MESSAGE;

@Getter
@Setter
public class DeliveryCostCalculatorRequest {

    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    private Long cityFromId;

    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    private Long cityToId;

    @Valid
    private DimensionsRequest dimensions;

    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    @Positive(message = NO_POSITIVE_NUMBER_KEY_ERROR_MESSAGE)
    private Double weight;

    public static DeliveryCostCalculatorRequest of(Long cityFromId, Long cityToId, DimensionsRequest dimensions, Double weight){
        DeliveryCostCalculatorRequest request = new DeliveryCostCalculatorRequest();
        request.setCityFromId(cityFromId);
        request.setCityToId(cityToId);
        request.setDimensions(dimensions);
        request.setWeight(weight);
        return request;
    }
}