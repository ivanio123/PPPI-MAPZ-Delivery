package com.pppi.novaposhta.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static com.pppi.novaposhta.exception.WrongInput.NO_POSITIVE_NUMBER;
import static com.pppi.novaposhta.exception.WrongInput.REQUIRED;

/**
 * Data Transfer Object to assemble data for delivering cost calculating.<br>
 * @author group2
 * @version 1.0
 * */
@Getter
@Setter
public class DeliveryCostCalculatorRequest {

    @NotNull(message = REQUIRED)
    private Long cityFromId;

    @NotNull(message = REQUIRED)
    private Long cityToId;

    @Valid
    private DimensionsRequest dimensions;

    @NotNull(message = REQUIRED)
    @Positive(message = NO_POSITIVE_NUMBER)
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