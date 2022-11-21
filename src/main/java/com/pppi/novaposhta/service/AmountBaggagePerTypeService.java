package com.pppi.novaposhta.service;

import com.pppi.novaposhta.custom.AmountBaggagePerType;
import com.pppi.novaposhta.repos.DeliveredBaggageRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AmountBaggagePerTypeService {

    private final DeliveredBaggageRepo baggageRepo;

    public List<AmountBaggagePerType> getBaggageReport(){
        return baggageRepo.countBaggagePerType();
    }
}