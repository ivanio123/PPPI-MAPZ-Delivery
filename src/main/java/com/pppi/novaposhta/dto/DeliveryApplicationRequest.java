package com.pppi.novaposhta.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.pppi.novaposhta.exception.WrongInputDataKeysConstants.REQUIRED_KEY_ERROR_MESSAGE;

@Getter
@Setter
public class DeliveryApplicationRequest {

    @Valid
    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    private AddressRequest senderAddress;

    @Valid
    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    private AddressRequest receiverAddress;

    @Valid
    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    private DeliveredBaggageRequest deliveredBaggageRequest;

    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate sendingDate;

    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private LocalDate receivingDate;

}
