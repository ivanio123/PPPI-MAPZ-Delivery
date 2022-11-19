package com.pppi.novaposhta.service;

import com.pppi.novaposhta.dto.DeliveryCostCalculatorRequest;
import com.pppi.novaposhta.dto.DeliveryCostCalculatorResponse;
import com.pppi.novaposhta.entity.Address;
import com.pppi.novaposhta.entity.City;
import com.pppi.novaposhta.exception.NoExistingCityException;
import com.pppi.novaposhta.exception.NoExistingDirectionException;
import com.pppi.novaposhta.exception.SameCityDirectionException;
import com.pppi.novaposhta.exception.WrongDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

@Service
public class DeliveryCostCalculatorService {
    @Autowired
    private DistanceFareService distanceFareService;

    @Autowired
    private WeightFareService weightFareService;

    @Autowired
    private DimensionsFareService dimensionsFareService;

    @Autowired
    private DirectionDeliveryService directionDeliveryService;

    @Autowired
    private CityService cityService;

    @Value("${spring.messages.basename}")
    private String messages;

    public DeliveryCostCalculatorResponse calculateCost(DeliveryCostCalculatorRequest calculatorRequest, Locale locale) throws WrongDataException {
        Objects.requireNonNull(calculatorRequest, "calculatorRequest cannot be null");

        ResourceBundle bundle = ResourceBundle.getBundle(messages, locale);

        Long cityFromId = calculatorRequest.getCityFromId();
        Long cityToId = calculatorRequest.getCityToId();
        requireDifferentCities(cityFromId, cityToId, bundle);

        City cityFrom = requireExistingCity(cityFromId);
        City cityTo = requireExistingCity(cityToId);

        City.Distance distance = directionDeliveryService.getDistanceBetweenCities(cityFrom, cityTo, locale);

        double distanceCost = distanceFareService.getPrice(distance.getDistance().intValue());
        double weightCost = weightFareService.getPrice(calculatorRequest.getWeight().intValue());
        double dimensionsCost = dimensionsFareService.getPrice(calculatorRequest.getDimensions().getVolume().intValue());

        double totalCost = distanceCost + weightCost + dimensionsCost;

        return new DeliveryCostCalculatorResponse(totalCost, distance);
    }

    public Double calculateWeightCost(Double weight){
        return weightFareService.getPrice(weight.intValue());
    }

    public Double calculateDimensionsCost(Double volume){
        return dimensionsFareService.getPrice(volume.intValue());
    }

    /**
     * calculate distance cost between two addresses
     * @param sender address of sender
     * @param receiver address of receiver
     * @return cost according to fare, if direction is within one city, return 0.0
     * @throws NoExistingDirectionException if direction between addresses doesn't exist in the database
     * */
    public Double calculateDistanceCost(Address sender, Address receiver) throws NoExistingDirectionException {
        City from = sender.getCity();
        City to = receiver.getCity();
        if (!Objects.equals(to.getId(), from.getId())){
            Double minDistance = directionDeliveryService.calculateMinDistance(from, to);
            if (minDistance.equals(Double.POSITIVE_INFINITY)){
                ResourceBundle bundle = ResourceBundle.getBundle(messages, Locale.UK);
                throw new NoExistingDirectionException(from, to, bundle);
            }
            return distanceFareService.getPrice(minDistance.intValue());
        }
        return 0.0;
    }

    private void requireDifferentCities(Long cityFromId, Long cityToId, ResourceBundle bundle) throws SameCityDirectionException {
        if (Objects.equals(cityFromId, cityToId)){
            throw new SameCityDirectionException(bundle);
        }
    }

    private City requireExistingCity(Long cityId) throws NoExistingCityException {
        City city = cityService.findCityById(cityId);
        if (Objects.isNull(city)){
            throw new NoExistingCityException();
        }
        return city;
    }

}
