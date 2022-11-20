package com.pppi.novaposhta.dto;

import com.pppi.novaposhta.entity.BaggageType;
import com.pppi.novaposhta.entity.DeliveryApplication;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Data Transfer Object to assemble filter for DeliveryApplication objects on delivery applications review page.<br>
 * @author group2
 * @version 1.0
 * */
@Getter
@Setter
public class DeliveryApplicationsReviewFilterRequest {

    private DeliveryApplication.State applicationState;

    private Long cityFromId;
    private Long cityToId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate sendingDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate receivingDate;

    private BaggageType baggageType;

}
