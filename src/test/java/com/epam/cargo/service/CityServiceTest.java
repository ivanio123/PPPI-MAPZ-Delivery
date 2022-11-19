package com.epam.cargo.service;

import com.epam.cargo.entity.City;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
class CityServiceTest {

    @Autowired
    private CityService cityService;

    @Test
    void addCity() {
        City city = new City("Vinytsia", "21012");
        cityService.addCity(city);
        boolean isPresent = !Objects.isNull(cityService.findCityByZipCode(city.getZipcode()));
        Assert.assertTrue(isPresent);
    }

    @Test
    void addCityFailed(){
        City city = new City("Vinytsia", "21012");
        cityService.addCity(city);
        boolean isAdded = cityService.addCity(city);
        Assert.assertFalse(isAdded);
    }
    @Test
    void findCityById(){
        String zipcode = "20301";
        City city1 = new City("Uman", zipcode);
        cityService.addCity(city1);

        City city2 = cityService.findCityByZipCode(zipcode);

        Assert.assertEquals(city1.getName(), city2.getName());
    }

}