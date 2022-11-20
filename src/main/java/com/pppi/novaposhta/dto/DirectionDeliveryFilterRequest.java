package com.pppi.novaposhta.dto;

import lombok.Getter;
import lombok.Setter;
/**
 * Data Transfer Object to assemble data for DirectionDelivery objects on directions page.<br>
 * @author group2
 * @version 1.0
 * */
@Getter
@Setter
public class DirectionDeliveryFilterRequest {
    private String senderCityName;
    private String receiverCityName;

}
