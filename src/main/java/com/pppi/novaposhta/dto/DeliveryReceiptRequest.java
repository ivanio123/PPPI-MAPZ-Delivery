package com.pppi.novaposhta.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static com.pppi.novaposhta.exception.WrongInput.NO_POSITIVE_NUMBER;
import static com.pppi.novaposhta.exception.WrongInput.REQUIRED;

/**
 * Data Transfer Object to assemble data for DeliveryReceipt on making delivery receipt page.<br>
 * @author group2
 * @version 1.0
 * */
@Getter
@Setter
public class DeliveryReceiptRequest {

    @NotNull(message = REQUIRED)
    @Positive(message = NO_POSITIVE_NUMBER)
    private Double price;

}
