package com.pppi.novaposhta.service;

import com.pppi.novaposhta.entity.DeliveredBaggage;
import com.pppi.novaposhta.repos.DeliveredBaggageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DeliveredBaggageService {

    @Autowired
    private DeliveredBaggageRepo deliveredBaggageRepo;

    public boolean addBaggage(DeliveredBaggage deliveredBaggage) {
        if (Objects.isNull(deliveredBaggage)){
            return false;
        }
        deliveredBaggageRepo.save(deliveredBaggage);
        return true;
    }
}
