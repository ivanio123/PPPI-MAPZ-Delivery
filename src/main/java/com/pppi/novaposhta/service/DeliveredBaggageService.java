package com.pppi.novaposhta.service;

import com.pppi.novaposhta.custom.IProfitBaggagePerType;
import com.pppi.novaposhta.dto.DeliveredBaggageRequest;
import com.pppi.novaposhta.entity.BaggageType;
import com.pppi.novaposhta.entity.DeliveredBaggage;
import com.pppi.novaposhta.repos.DeliveredBaggageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service class for managing DeliveredBaggage objects.<br>
 * @author group2
 * @see DeliveredBaggage
 * @version 1.0
 * */
@Service
public class DeliveredBaggageService {

    private final DeliveredBaggageRepo deliveredBaggageRepo;

    public DeliveredBaggageService(DeliveredBaggageRepo deliveredBaggageRepo) {
        this.deliveredBaggageRepo = deliveredBaggageRepo;
    }

    public Optional<DeliveredBaggage> findById(Long id){
        return deliveredBaggageRepo.findById(id);
    }

    /**
     * Save delivered baggage in the database
     * @param deliveredBaggage baggage to add
     * @throws IllegalArgumentException whereas any of mandatory attributes are missing
     * @return true whether deliveredBaggage was saved successful, false if param is null
     * */
    public boolean save(DeliveredBaggage deliveredBaggage) {
        if (Objects.isNull(deliveredBaggage)){
            return false;
        }
        requireValidBaggage(deliveredBaggage);
        deliveredBaggageRepo.save(deliveredBaggage);
        return true;
    }

    private void requireValidBaggage(DeliveredBaggage deliveredBaggage) {
        Optional.ofNullable(deliveredBaggage.getWeight()).orElseThrow(IllegalArgumentException::new);
        Optional.ofNullable(deliveredBaggage.getVolume()).orElseThrow(IllegalArgumentException::new);
        Optional.ofNullable(deliveredBaggage.getType()).orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Updates state of already existing baggage
     * @param deliveredBaggage updating baggage object
     * @param deliveredBaggageRequest updating data
     * @return updated DeliveredBaggage object
     * */
    public DeliveredBaggage update(DeliveredBaggage deliveredBaggage, DeliveredBaggageRequest deliveredBaggageRequest) {
        Objects.requireNonNull(deliveredBaggage, "Delivered Baggage from db cannot be null");
        Objects.requireNonNull(deliveredBaggageRequest, "Delivered Baggage Request cannot be null");
        if (deliveredBaggageRepo.findById(deliveredBaggage.getId()).isEmpty()){
            throw new IllegalArgumentException("Param deliveredBaggage doesn't exist in db");
        }
        DeliveredBaggage updated = ServiceUtils.createDeliveredBaggage(deliveredBaggageRequest);
        updated.setId(deliveredBaggage.getId());
        return deliveredBaggageRepo.save(updated);
    }

    /**
     * Gives report of profit {@link DeliveredBaggage} per each {@link BaggageType}.
     * @return {@link List} of {@link IProfitBaggagePerType} objects
     * */
    public List<IProfitBaggagePerType> profitBaggageReport(){
        return deliveredBaggageRepo.profitBaggagePerType();
    }

}
