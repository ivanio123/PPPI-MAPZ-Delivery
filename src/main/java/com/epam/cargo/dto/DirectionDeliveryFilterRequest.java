package com.epam.cargo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirectionDeliveryFilterRequest {
    private String senderCityName;
    private String receiverCityName;

}
