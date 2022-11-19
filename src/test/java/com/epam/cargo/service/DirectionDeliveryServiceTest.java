package com.epam.cargo.service;

import com.epam.cargo.entity.City;
import com.epam.cargo.entity.DirectionDelivery;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
class DirectionDeliveryServiceTest {

    private static final String zipcodeOfKyiv = "01001-06999";
    private static final String zipcodeOfVinnytsia = "21012";
    private static final String zipcodeOfUman = "20301";
    private static final String zipcodeOfKhmelnytskyi = "29000-29027";

    @Autowired
    private DirectionDeliveryService directionDeliveryService;

    @Autowired
    private CityService  cityService;

    @Test
    public void testCaseAddDirection() {

        double distance = 160.1;

        DirectionDelivery direction = new DirectionDelivery(
                cityService.findCityByZipCode(zipcodeOfVinnytsia),
                cityService.findCityByZipCode(zipcodeOfUman),
                distance
        );

        directionDeliveryService.addDirection(direction);

        Assert.assertEquals(direction, directionDeliveryService.findById(direction.getId()));
    }

    @Test
    public void testCaseAddDirectionFromParams(){

        City senderCity = cityService.findCityByZipCode(zipcodeOfKyiv);
        City receiverCity = cityService.findCityByZipCode(zipcodeOfVinnytsia);
        double distance = 269.1;

        directionDeliveryService.addDirection(senderCity, receiverCity, distance);

        Assert.assertFalse(Objects.isNull(directionDeliveryService.findBySenderCityAndReceiverCity(senderCity, receiverCity)));

    }

    @Test
    public void testCaseAddDirectionAndDeleteThan() {

        double distance = 322;

        DirectionDelivery direction = new DirectionDelivery(
                cityService.findCityByZipCode(zipcodeOfKyiv),
                cityService.findCityByZipCode(zipcodeOfKhmelnytskyi),
                distance
        );

        directionDeliveryService.addDirection(direction);

        Assert.assertEquals(direction, directionDeliveryService.findById(direction.getId()));

        directionDeliveryService.deleteDirection(direction);

        Assert.assertThrows(NoSuchElementException.class, ()->directionDeliveryService.findById(direction.getId()));
    }

    @Test
    public void testCaseDirectionFailed(){
        DirectionDelivery direction1 = new DirectionDelivery();

        Assert.assertThrows(NoSuchElementException.class, ()->directionDeliveryService.addDirection(direction1));

        City city1 = cityService.findCityByZipCode(zipcodeOfVinnytsia);
        City city2 = cityService.findCityByZipCode(zipcodeOfVinnytsia);
        double distance = 0;

        DirectionDelivery direction2 = new DirectionDelivery(city1, city2, distance);
        Assert.assertThrows(IllegalArgumentException.class, ()->directionDeliveryService.addDirection(direction2));

    }

}