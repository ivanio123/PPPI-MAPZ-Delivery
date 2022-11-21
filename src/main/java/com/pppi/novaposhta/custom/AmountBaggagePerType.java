package com.pppi.novaposhta.custom;

import com.pppi.novaposhta.entity.BaggageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AmountBaggagePerType {

    private BaggageType type;

    private Long amount;

}