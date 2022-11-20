package com.pppi.novaposhta.service;

import com.pppi.novaposhta.entity.WeightFare;
import com.pppi.novaposhta.repos.WeightFareRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Service class for obtaining WeightFare and calculating its price.<br>
 * @author group2
 * @see WeightFare
 * @version 1.0
 * */
@Service
public class WeightFareService {
    @Autowired
    private WeightFareRepo weightFareRepo;

    public List<WeightFare> findAllFares() {
        return weightFareRepo.findAll();
    }

    public WeightFare findFareByWeight(Integer weight){
        requireOnlyPositive(weight);

        WeightFare fare = weightFareRepo.findFareByWeight(weight);
        if (Objects.isNull(fare)){
            fare = weightFareRepo.findMaxFare();
        }

        return fare;
    }

    /**
     * @param weight of baggage in kg
     * @return a calculated price according to given weight from weight fares table,
     * if weight is max for every max value over max value will be added a corresponding price from max fare
     * */
    public Double getPrice(Integer weight){
        double price = 0.0;
        WeightFare fare = findFareByWeight(weight);

        do {
            price += fare.getPrice();
            weight -= fare.getWeightTo();
        } while(weight != 0 && weight >= fare.getWeightFrom());

        return price;
    }

    private void requireOnlyPositive(Integer weight) {
        if (weight < 0){
            throw new IllegalArgumentException("weight cannot be null");
        }
    }

}
