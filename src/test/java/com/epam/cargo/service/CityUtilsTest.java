package com.epam.cargo.service;

import com.epam.cargo.entity.City;
import com.epam.cargo.entity.DirectionDelivery;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.cargo.service.CityZipcodesConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CityUtilsTest {

    @Autowired
    private DirectionDeliveryService directionDeliveryService;
    
    @Autowired
    private CityService cityService;

    private static Stream<Arguments> testDistanceCalculationCases() {
        return Stream.of(
                Arguments.of(KYIV_ZIPCODE, CHERKASY_ZIPCODE, 192.3),
                Arguments.of(KYIV_ZIPCODE, UMAN_ZIPCODE, 375.9),
                Arguments.of(KYIV_ZIPCODE, VINNYTISA_ZIPCODE, 268.6),
                Arguments.of(KYIV_ZIPCODE, ZHYTOMYR_ZIPCODE, 139.9),
                Arguments.of(UZHOROD_ZIPCODE, KHARKIV_ZIPCODE, 1355.5),
                Arguments.of(LUTSK_ZIPCODE, SUMY_ZIPCODE, 855.0),
                Arguments.of(SUMY_ZIPCODE, LUTSK_ZIPCODE, 855.0),
                Arguments.of(KHARKIV_ZIPCODE, CHERNIVTSI_ZIPCODE, 1027.7),
                Arguments.of(ZAPORIZHZHIA_ZIPCODE, UZHOROD_ZIPCODE, 1314.0),
                Arguments.of(CHERNIVTSI_ZIPCODE, LUTSK_ZIPCODE, 400.5),
                Arguments.of(POLTAVA_ZIPCODE, RIVNE_ZIPCODE, 753.0),
                Arguments.of(POLTAVA_ZIPCODE, KHMELNYTSKIY_ZIPCODE, 696.7)
        );
    }

    private static Stream<Arguments> testSmallestRouteBuildingCases() {
        return Stream.of(
                Arguments.of(KYIV_ZIPCODE, CHERKASY_ZIPCODE, Arrays.asList(KYIV_ZIPCODE, CHERKASY_ZIPCODE)),
                Arguments.of(KYIV_ZIPCODE, UMAN_ZIPCODE,  Arrays.asList(KYIV_ZIPCODE, CHERKASY_ZIPCODE, UMAN_ZIPCODE)),
                Arguments.of(KYIV_ZIPCODE, VINNYTISA_ZIPCODE, Arrays.asList(KYIV_ZIPCODE, ZHYTOMYR_ZIPCODE, VINNYTISA_ZIPCODE)),
                Arguments.of(KYIV_ZIPCODE, ZHYTOMYR_ZIPCODE,  Arrays.asList(KYIV_ZIPCODE, ZHYTOMYR_ZIPCODE)),
                Arguments.of(UZHOROD_ZIPCODE, KHARKIV_ZIPCODE, Arrays.asList(
                                UZHOROD_ZIPCODE, IVANOFRANKIVSK_ZIPCODE, TERNOPIL_ZIPCODE, KHMELNYTSKIY_ZIPCODE, VINNYTISA_ZIPCODE,
                                UMAN_ZIPCODE, CHERKASY_ZIPCODE, POLTAVA_ZIPCODE, KHARKIV_ZIPCODE
                        )
                ),
                Arguments.of(LUTSK_ZIPCODE, SUMY_ZIPCODE, Arrays.asList(
                                LUTSK_ZIPCODE, RIVNE_ZIPCODE, ZHYTOMYR_ZIPCODE,
                                KYIV_ZIPCODE, CHERNIHIV_ZIPCODE, SUMY_ZIPCODE
                        )

                ),
                Arguments.of(SUMY_ZIPCODE, LUTSK_ZIPCODE, Arrays.asList(
                                SUMY_ZIPCODE, CHERNIHIV_ZIPCODE, KYIV_ZIPCODE,
                                ZHYTOMYR_ZIPCODE, RIVNE_ZIPCODE, LUTSK_ZIPCODE
                        )

                ),
                Arguments.of(KHARKIV_ZIPCODE, CHERNIVTSI_ZIPCODE, Arrays.asList(
                                KHARKIV_ZIPCODE, POLTAVA_ZIPCODE, CHERKASY_ZIPCODE, UMAN_ZIPCODE,
                                VINNYTISA_ZIPCODE, KHMELNYTSKIY_ZIPCODE, KAMIANETS_ZIPCODE, CHERNIVTSI_ZIPCODE
                        )

                ),
                Arguments.of(ZAPORIZHZHIA_ZIPCODE, UZHOROD_ZIPCODE,  Arrays.asList(
                        ZAPORIZHZHIA_ZIPCODE, DNIPRO_ZIPCODE, KROPYVNYTSKYI_ZIPCODE, UMAN_ZIPCODE,
                        VINNYTISA_ZIPCODE, KHMELNYTSKIY_ZIPCODE, TERNOPIL_ZIPCODE, IVANOFRANKIVSK_ZIPCODE,
                        UZHOROD_ZIPCODE
                        )
                ),
                Arguments.of(CHERNIVTSI_ZIPCODE, LUTSK_ZIPCODE,  Arrays.asList(
                        CHERNIVTSI_ZIPCODE, TERNOPIL_ZIPCODE, RIVNE_ZIPCODE, LUTSK_ZIPCODE
                        )

                ),
                Arguments.of(POLTAVA_ZIPCODE, RIVNE_ZIPCODE,  Arrays.asList(
                        POLTAVA_ZIPCODE, CHERKASY_ZIPCODE, KYIV_ZIPCODE, ZHYTOMYR_ZIPCODE, RIVNE_ZIPCODE
                        )

                ),
                Arguments.of(POLTAVA_ZIPCODE, KHMELNYTSKIY_ZIPCODE, Arrays.asList(
                        POLTAVA_ZIPCODE, CHERKASY_ZIPCODE, UMAN_ZIPCODE, VINNYTISA_ZIPCODE, KHMELNYTSKIY_ZIPCODE
                        )

                )

        );
    }


    @ParameterizedTest
    @MethodSource(value = "testDistanceCalculationCases")
    public void testDistanceCalculation(String zipcode1, String zipcode2, Double expectedDistance){
        City city1 = cityService.findCityByZipCode(zipcode1);
        City city2 = cityService.findCityByZipCode(zipcode2);
        List<DirectionDelivery> directions = directionDeliveryService.findAll(Locale.UK);
        Double distance = CityUtils.getDistance(city1, city2, directions).getDistance();
        Assert.assertEquals(expectedDistance, distance);
    }

    @ParameterizedTest
    @MethodSource(value = "testSmallestRouteBuildingCases")
    public void testSmallestRoutBuilding(String zipcode1, String zipcode2, List<String> expectedZipcodes){
        City city1 = cityService.findCityByZipCode(zipcode1);
        City city2 = cityService.findCityByZipCode(zipcode2);
        List<DirectionDelivery> directions = directionDeliveryService.findAll(Locale.UK);
        List<City> route = CityUtils.getDistance(city1, city2, directions).getRoute();
        List<String> zipcodes = route.stream().map(City::getZipcode).collect(Collectors.toList());
        Assert.assertEquals(expectedZipcodes, zipcodes);
    }
}