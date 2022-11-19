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
class WeightFareServiceTest {

    @Autowired
    private WeightFareService fareService;

    private static Stream<Arguments> testGetPriceCases() {
        return Stream.of(
                Arguments.of(1, 20.0),
                Arguments.of(2, 20.0),
                Arguments.of(3, 30.0),
                Arguments.of(10, 60.0),
                Arguments.of(20, 60.0),
                Arguments.of(30, 120.0),
                Arguments.of(50, 120.0),
                Arguments.of(100, 150.0),
                Arguments.of(150, 150.0),
                Arguments.of(199, 150.0),
                Arguments.of(200, 300.0),
                Arguments.of(300, 450.0),
                Arguments.of(350, 450.0),
                Arguments.of(400, 600.0)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "testGetPriceCases")
    public void testGetPrice(Integer weight, Double price){
        Assert.assertEquals(price, fareService.getPrice(weight));
    }

}