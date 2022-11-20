package com.pppi.novaposhta.service;

import com.pppi.novaposhta.entity.City;
import com.pppi.novaposhta.mock.CityMockEnvironment;
import com.pppi.novaposhta.repos.CityRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
class CityServiceTest {

    @Autowired
    private CityService cityService;

    @MockBean
    private CityRepo cityRepo;

    @BeforeEach
    public void setUp(){
        CityMockEnvironment cityMockEnvironment = new CityMockEnvironment();
        cityMockEnvironment.mockCityRepoBean(cityRepo);
    }

    @Test
    void addCity() {
        City city = getTestCity();
        cityService.addCity(city);
        boolean isPresent = !Objects.isNull(cityService.findCityByZipCode(city.getZipcode()));
        Assertions.assertTrue(isPresent);
    }

    @Test
    void addCityFailed(){
        City city = getTestCity();
        cityService.addCity(city);
        boolean isAdded = cityService.addCity(city);
        Assertions.assertFalse(isAdded);
    }
    @Test
    void findCityById(){

        City city1 = getTestCity();
        cityService.addCity(city1);

        City city2 = cityService.findCityByZipCode(city1.getZipcode());

        Assertions.assertEquals(city1.getName(), city2.getName());
    }

    private City getTestCity() {
        return new City("TestCity", "12345");
    }

}