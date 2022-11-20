package com.pppi.novaposhta.service;

import com.pppi.novaposhta.entity.City;
import com.pppi.novaposhta.entity.DirectionDelivery;
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

@RunWith(SpringRunner.class)
@SpringBootTest
class CityUtilsTest {

    @Autowired
    private DirectionDeliveryService directionDeliveryService;
    
    @Autowired
    private CityService cityService;

    private static Stream<Arguments> testDistanceCalculationCases() {
        return Stream.of(
                Arguments.of(CityZipcodesConstants.KYIV_ZIPCODE, CityZipcodesConstants.CHERKASY_ZIPCODE, 192.3),
                Arguments.of(CityZipcodesConstants.KYIV_ZIPCODE, CityZipcodesConstants.UMAN_ZIPCODE, 375.9),
                Arguments.of(CityZipcodesConstants.KYIV_ZIPCODE, CityZipcodesConstants.VINNYTISA_ZIPCODE, 268.6),
                Arguments.of(CityZipcodesConstants.KYIV_ZIPCODE, CityZipcodesConstants.ZHYTOMYR_ZIPCODE, 139.9),
                Arguments.of(CityZipcodesConstants.UZHOROD_ZIPCODE, CityZipcodesConstants.KHARKIV_ZIPCODE, 1355.5),
                Arguments.of(CityZipcodesConstants.LUTSK_ZIPCODE, CityZipcodesConstants.SUMY_ZIPCODE, 855.0),
                Arguments.of(CityZipcodesConstants.SUMY_ZIPCODE, CityZipcodesConstants.LUTSK_ZIPCODE, 855.0),
                Arguments.of(CityZipcodesConstants.KHARKIV_ZIPCODE, CityZipcodesConstants.CHERNIVTSI_ZIPCODE, 1027.7),
                Arguments.of(CityZipcodesConstants.ZAPORIZHZHIA_ZIPCODE, CityZipcodesConstants.UZHOROD_ZIPCODE, 1314.0),
                Arguments.of(CityZipcodesConstants.CHERNIVTSI_ZIPCODE, CityZipcodesConstants.LUTSK_ZIPCODE, 400.5),
                Arguments.of(CityZipcodesConstants.POLTAVA_ZIPCODE, CityZipcodesConstants.RIVNE_ZIPCODE, 753.0),
                Arguments.of(CityZipcodesConstants.POLTAVA_ZIPCODE, CityZipcodesConstants.KHMELNYTSKIY_ZIPCODE, 696.7)
        );
    }

    private static Stream<Arguments> testSmallestRouteBuildingCases() {
        return Stream.of(
                Arguments.of(CityZipcodesConstants.KYIV_ZIPCODE, CityZipcodesConstants.CHERKASY_ZIPCODE, Arrays.asList(CityZipcodesConstants.KYIV_ZIPCODE, CityZipcodesConstants.CHERKASY_ZIPCODE)),
                Arguments.of(CityZipcodesConstants.KYIV_ZIPCODE, CityZipcodesConstants.UMAN_ZIPCODE,  Arrays.asList(CityZipcodesConstants.KYIV_ZIPCODE, CityZipcodesConstants.CHERKASY_ZIPCODE, CityZipcodesConstants.UMAN_ZIPCODE)),
                Arguments.of(CityZipcodesConstants.KYIV_ZIPCODE, CityZipcodesConstants.VINNYTISA_ZIPCODE, Arrays.asList(CityZipcodesConstants.KYIV_ZIPCODE, CityZipcodesConstants.ZHYTOMYR_ZIPCODE, CityZipcodesConstants.VINNYTISA_ZIPCODE)),
                Arguments.of(CityZipcodesConstants.KYIV_ZIPCODE, CityZipcodesConstants.ZHYTOMYR_ZIPCODE,  Arrays.asList(CityZipcodesConstants.KYIV_ZIPCODE, CityZipcodesConstants.ZHYTOMYR_ZIPCODE)),
                Arguments.of(CityZipcodesConstants.UZHOROD_ZIPCODE, CityZipcodesConstants.KHARKIV_ZIPCODE, Arrays.asList(
                                CityZipcodesConstants.UZHOROD_ZIPCODE, CityZipcodesConstants.IVANOFRANKIVSK_ZIPCODE, CityZipcodesConstants.TERNOPIL_ZIPCODE, CityZipcodesConstants.KHMELNYTSKIY_ZIPCODE, CityZipcodesConstants.VINNYTISA_ZIPCODE,
                                CityZipcodesConstants.UMAN_ZIPCODE, CityZipcodesConstants.CHERKASY_ZIPCODE, CityZipcodesConstants.POLTAVA_ZIPCODE, CityZipcodesConstants.KHARKIV_ZIPCODE
                        )
                ),
                Arguments.of(CityZipcodesConstants.LUTSK_ZIPCODE, CityZipcodesConstants.SUMY_ZIPCODE, Arrays.asList(
                                CityZipcodesConstants.LUTSK_ZIPCODE, CityZipcodesConstants.RIVNE_ZIPCODE, CityZipcodesConstants.ZHYTOMYR_ZIPCODE,
                                CityZipcodesConstants.KYIV_ZIPCODE, CityZipcodesConstants.CHERNIHIV_ZIPCODE, CityZipcodesConstants.SUMY_ZIPCODE
                        )

                ),
                Arguments.of(CityZipcodesConstants.SUMY_ZIPCODE, CityZipcodesConstants.LUTSK_ZIPCODE, Arrays.asList(
                                CityZipcodesConstants.SUMY_ZIPCODE, CityZipcodesConstants.CHERNIHIV_ZIPCODE, CityZipcodesConstants.KYIV_ZIPCODE,
                                CityZipcodesConstants.ZHYTOMYR_ZIPCODE, CityZipcodesConstants.RIVNE_ZIPCODE, CityZipcodesConstants.LUTSK_ZIPCODE
                        )

                ),
                Arguments.of(CityZipcodesConstants.KHARKIV_ZIPCODE, CityZipcodesConstants.CHERNIVTSI_ZIPCODE, Arrays.asList(
                                CityZipcodesConstants.KHARKIV_ZIPCODE, CityZipcodesConstants.POLTAVA_ZIPCODE, CityZipcodesConstants.CHERKASY_ZIPCODE, CityZipcodesConstants.UMAN_ZIPCODE,
                                CityZipcodesConstants.VINNYTISA_ZIPCODE, CityZipcodesConstants.KHMELNYTSKIY_ZIPCODE, CityZipcodesConstants.KAMIANETS_ZIPCODE, CityZipcodesConstants.CHERNIVTSI_ZIPCODE
                        )

                ),
                Arguments.of(CityZipcodesConstants.ZAPORIZHZHIA_ZIPCODE, CityZipcodesConstants.UZHOROD_ZIPCODE,  Arrays.asList(
                        CityZipcodesConstants.ZAPORIZHZHIA_ZIPCODE, CityZipcodesConstants.DNIPRO_ZIPCODE, CityZipcodesConstants.KROPYVNYTSKYI_ZIPCODE, CityZipcodesConstants.UMAN_ZIPCODE,
                        CityZipcodesConstants.VINNYTISA_ZIPCODE, CityZipcodesConstants.KHMELNYTSKIY_ZIPCODE, CityZipcodesConstants.TERNOPIL_ZIPCODE, CityZipcodesConstants.IVANOFRANKIVSK_ZIPCODE,
                        CityZipcodesConstants.UZHOROD_ZIPCODE
                        )
                ),
                Arguments.of(CityZipcodesConstants.CHERNIVTSI_ZIPCODE, CityZipcodesConstants.LUTSK_ZIPCODE,  Arrays.asList(
                        CityZipcodesConstants.CHERNIVTSI_ZIPCODE, CityZipcodesConstants.TERNOPIL_ZIPCODE, CityZipcodesConstants.RIVNE_ZIPCODE, CityZipcodesConstants.LUTSK_ZIPCODE
                        )

                ),
                Arguments.of(CityZipcodesConstants.POLTAVA_ZIPCODE, CityZipcodesConstants.RIVNE_ZIPCODE,  Arrays.asList(
                        CityZipcodesConstants.POLTAVA_ZIPCODE, CityZipcodesConstants.CHERKASY_ZIPCODE, CityZipcodesConstants.KYIV_ZIPCODE, CityZipcodesConstants.ZHYTOMYR_ZIPCODE, CityZipcodesConstants.RIVNE_ZIPCODE
                        )

                ),
                Arguments.of(CityZipcodesConstants.POLTAVA_ZIPCODE, CityZipcodesConstants.KHMELNYTSKIY_ZIPCODE, Arrays.asList(
                        CityZipcodesConstants.POLTAVA_ZIPCODE, CityZipcodesConstants.CHERKASY_ZIPCODE, CityZipcodesConstants.UMAN_ZIPCODE, CityZipcodesConstants.VINNYTISA_ZIPCODE, CityZipcodesConstants.KHMELNYTSKIY_ZIPCODE
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