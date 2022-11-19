package com.pppi.novaposhta.service;

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
class DimensionsFareServiceTest {

    @Autowired
    private DimensionsFareService fareService;

    private static Stream<Arguments> testGetPriceCases() {
        return Stream.of(
                Arguments.of(1000, 10.0),
                Arguments.of(4000, 10.0),
                Arguments.of(4999, 10.0),
                Arguments.of(5000, 20.0),
                Arguments.of(20000, 35.0),
                Arguments.of(50000, 35.0),
                Arguments.of(100000, 60.0),
                Arguments.of(500000, 60.0),
                Arguments.of(1000000, 80.0),
                Arguments.of(2000000, 160.0),
                Arguments.of(2500000, 160.0),
                Arguments.of(3000000, 240.0)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "testGetPriceCases")
    void testGetPrice(Integer volume, Double price) {
        Assert.assertEquals(price, fareService.getPrice(volume));
    }
}