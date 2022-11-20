package com.pppi.novaposhta.dto;

import com.pppi.novaposhta.exception.WrongInputDataKeysConstants;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class DeliveryCostCalculatorRequest {

    @NotNull(message = WrongInputDataKeysConstants.REQUIRED_KEY_ERROR_MESSAGE)
    private Long cityFromId;

    @NotNull(message = WrongInputDataKeysConstants.REQUIRED_KEY_ERROR_MESSAGE)
    private Long cityToId;

    @Valid
    private DimensionsRequest dimensions;

    @NotNull(message = WrongInputDataKeysConstants.REQUIRED_KEY_ERROR_MESSAGE)
    @Positive(message = WrongInputDataKeysConstants.NO_POSITIVE_NUMBER_KEY_ERROR_MESSAGE)
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