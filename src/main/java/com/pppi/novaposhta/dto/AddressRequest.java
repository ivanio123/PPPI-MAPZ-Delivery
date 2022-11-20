package com.pppi.novaposhta.dto;

import com.pppi.novaposhta.entity.BaggageType;
import com.pppi.novaposhta.exception.WrongInput.*;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static com.pppi.novaposhta.exception.WrongInput.REQUIRED;

/**
 * Data Transfer Object to assemble address.<br>
 * @author group2
 * @version 1.0
 * */
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class AddressRequest {

    @NotNull(message = REQUIRED)
    private Long cityId;

    @NotNull(message = REQUIRED)
    @NotBlank(message = REQUIRED)
    private String streetName;

    @NotNull(message = REQUIRED)
    @NotBlank(message = REQUIRED)
    private String houseNumber;
}
