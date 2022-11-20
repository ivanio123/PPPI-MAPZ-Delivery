package com.pppi.novaposhta.service;

import com.pppi.novaposhta.dto.DeliveredBaggageRequest;
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

    public DeliveredBaggage update(DeliveredBaggage deliveredBaggage, DeliveredBaggageRequest deliveredBaggageRequest) {
        Objects.requireNonNull(deliveredBaggage, "Delivered Baggage from db cannot be null");
        Objects.requireNonNull(deliveredBaggageRequest, "Delivered Baggage Request cannot be null");
        DeliveredBaggage updated = ServiceUtils.createDeliveredBaggage(deliveredBaggageRequest);
        updated.setId(deliveredBaggage.getId());
        return deliveredBaggageRepo.save(updated);
    }
}
