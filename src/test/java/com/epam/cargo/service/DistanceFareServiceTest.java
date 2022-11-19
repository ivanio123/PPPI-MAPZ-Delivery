package com.epam.cargo.service;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
class DistanceFareServiceTest {

    @Autowired
    private DistanceFareService fareService;

    private static Stream<Arguments> testGetPriceCases() {
        return Stream.of(
                Arguments.of(0, 30.0),
                Arguments.of(10, 30.0),
                Arguments.of(20, 50.0),
                Arguments.of(30, 50.0),
                Arguments.of(50, 80.0),
                Arguments.of(100, 80.0),
                Arguments.of(200, 150.0),
                Arguments.of(500, 150.0),
                Arguments.of(1000, 200.0),
                Arguments.of(2000, 200.0)
                );
    }

    @ParameterizedTest
    @MethodSource(value = "testGetPriceCases")
    public void testGetPrice(Integer distance, Double expectedPrice){
        Double price = fareService.findFareByDistance(distance).getPrice();
        Assert.assertEquals(expectedPrice, price);
    }

}