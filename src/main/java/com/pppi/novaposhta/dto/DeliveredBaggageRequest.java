package com.pppi.novaposhta.dto;

import com.pppi.novaposhta.entity.BaggageType;
import com.pppi.novaposhta.exception.WrongInput.*;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static com.pppi.novaposhta.exception.WrongInput.NO_POSITIVE_NUMBER;
import static com.pppi.novaposhta.exception.WrongInput.REQUIRED;

/**
 * Data Transfer Object to assemble baggage on making delivery application page.<br>
 * @author group2
 * @version 1.0
 * */
@Getter
@Setter
@Builder
public class DeliveredBaggageRequest {

    @NotNull(message = REQUIRED)
    @Positive(message = NO_POSITIVE_NUMBER)
    private Double weight;

    @NotNull(message = REQUIRED)
    @Positive(message = NO_POSITIVE_NUMBER)
    private Double volume;

    @NotNull(message = REQUIRED)
    private BaggageType type;

    private String description;

    public DeliveredBaggageRequest() {
    }

    public DeliveredBaggageRequest(Double weight, Double volume, BaggageType type, String description) {
        this.weight = weight;
        this.volume = volume;
        this.type = type;
        this.description = description;
    }
}
