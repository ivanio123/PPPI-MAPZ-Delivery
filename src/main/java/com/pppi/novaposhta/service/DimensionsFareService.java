package com.pppi.novaposhta.service;

import com.pppi.novaposhta.entity.DimensionsFare;
import com.pppi.novaposhta.repos.DimensionsFareRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DimensionsFareService {

    @Autowired
    private DimensionsFareRepo dimensionsFareRepo;

    public List<DimensionsFare> findAllFares() {
        return dimensionsFareRepo.findAll();
    }

    public DimensionsFare findFareByVolume(Integer volume){
        requireOnlyPositive(volume);

        DimensionsFare fare = dimensionsFareRepo.findFareByDistance(volume);
        if (Objects.isNull(fare)){
            fare = dimensionsFareRepo.findMaxFare();
        }
        return fare;
    }

    /**
     * @param volume of baggage in cm^3
     * @return a calculated price according to given volume from volume fares table,
     * if volume is max for every max value over max value will be added a corresponding price from max fare
     * */
    public Double getPrice(Integer volume){
        double price = 0.0;
        DimensionsFare fare = findFareByVolume(volume);

        while(volume != 0 && volume >= fare.getDimensionsFrom()){
            price += fare.getPrice();
            volume -= fare.getDimensionsTo();
        }
        return price;
    }

    private void requireOnlyPositive(Integer volume) {
        if (volume < 0){
            throw new IllegalArgumentException("volume cannot be negative");
        }
    }

}
