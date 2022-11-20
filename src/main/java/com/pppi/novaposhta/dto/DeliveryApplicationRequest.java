package com.pppi.novaposhta.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.pppi.novaposhta.exception.WrongInput.REQUIRED;

/**
 * Data Transfer Object to assemble application data from making delivery application page<br>
 * @author group2
 * @version 1.0
 * */
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class DeliveryApplicationRequest {

    @Valid
    @NotNull(message = REQUIRED)
    private AddressRequest senderAddress;

    @Valid
    @NotNull(message = REQUIRED)
    private AddressRequest receiverAddress;

    @Valid
    @NotNull(message = REQUIRED)
    private DeliveredBaggageRequest deliveredBaggageRequest;

    @NotNull(message = REQUIRED)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate sendingDate;

    @NotNull(message = REQUIRED)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate receivingDate;

}
