package com.pppi.novaposhta.custom;

import com.pppi.novaposhta.entity.BaggageType;

public interface IProfitBaggagePerType {

    BaggageType getType();

    Long getProfit();
}
