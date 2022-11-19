package com.epam.cargo.service;

import com.epam.cargo.dto.DeliveryCostCalculatorRequest;
import com.epam.cargo.dto.DeliveryCostCalculatorResponse;
import com.epam.cargo.dto.DimensionsRequest;
import com.epam.cargo.entity.City;
import com.epam.cargo.entity.DirectionDelivery;
import com.epam.cargo.exception.WrongDataException;
import com.epam.cargo.repos.CityRepo;
import com.epam.cargo.repos.DirectionDeliveryRepo;
import com.epam.cargo.util.CsvFilesCityReader;
import com.epam.cargo.util.CsvFilesDirectionDeliveryReader;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.epam.cargo.service.CityZipcodesConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeliveryCostCalculatorServiceTest {

    private static final String PATH_TO_CSV_FILES = "src/test/resources/static/";
    private static final String CITIES_CSV = "cities_table.csv";
    private static final String DIRECTIONS_CSV = "directions_table.csv";

    @Autowired
    private DeliveryCostCalculatorService deliveryCostCalculatorService;

    @MockBean
    private CityRepo cityRepo;

    @MockBean
    private DirectionDeliveryRepo directionDeliveryRepo;

    private static final Map<String, City> citiesByZipcodes = new HashMap<>();

    private static final Map<Long, City> citiesById = new HashMap<>();
    private static final List<DirectionDelivery> directions = new ArrayList<>();


    @BeforeAll
    public static void initTestEnvironment(){
        CsvFilesCityReader.readCitiesCsv(PATH_TO_CSV_FILES + CITIES_CSV, citiesById, citiesByZipcodes);
        CsvFilesDirectionDeliveryReader.readDirectionsDeliveryCsv(PATH_TO_CSV_FILES + DIRECTIONS_CSV, citiesById, directions);
    }

    private static Stream<Arguments> calculateCostCases() {

        return Stream.of(
                Arguments.of(
                        DeliveryCostCalculatorRequest.of(3L, 1L, DimensionsRequest.of(30.0, 20.0, 4.0), 3.0),
                        DeliveryCostCalculatorResponse.of(new City.Distance(getCity(UMAN_ZIPCODE), getCity(VINNYTISA_ZIPCODE), 160.1, getCitiesByZipcodes(UMAN_ZIPCODE, VINNYTISA_ZIPCODE)), 120.0
                        )
                ),
                Arguments.of(
                        DeliveryCostCalculatorRequest.of(2L, 3L, DimensionsRequest.of(100.0, 40.0, 30.0), 50.0),
                        DeliveryCostCalculatorResponse.of(new City.Distance(getCity(KYIV_ZIPCODE), getCity(UMAN_ZIPCODE), 375.9, getCitiesByZipcodes(KYIV_ZIPCODE, CHERKASY_ZIPCODE, UMAN_ZIPCODE)), 330.0)
                )
        );
    }

    private static List<City> getCitiesByZipcodes(String... zipcodes){
        return Arrays.stream(zipcodes).map(DeliveryCostCalculatorServiceTest::getCity).collect(Collectors.toList());
    }

    private static City getCity(String zipcode) {
        return citiesByZipcodes.get(zipcode);
    }

    //TODO mock for all fares
    @ParameterizedTest
    @MethodSource(value = "calculateCostCases")
    void calculateCost(DeliveryCostCalculatorRequest request, DeliveryCostCalculatorResponse expectedResponse) throws WrongDataException {
        Mockito.doReturn(new ArrayList<>(citiesById.values()))
                .when(cityRepo)
                .findAll();

        for (City c : citiesById.values()) {
            Mockito.doReturn(Optional.of(c))
                    .when(cityRepo)
                    .findById(c.getId());
        }

        Mockito.doReturn(new ArrayList<>(directions))
                .when(directionDeliveryRepo)
                .findAll();

        DeliveryCostCalculatorResponse response = deliveryCostCalculatorService.calculateCost(request, Locale.UK);
        Assert.assertEquals(expectedResponse.getCost(), response.getCost());
        Assert.assertEquals(expectedResponse.getDistance(), response.getDistance());
    }
    
    //TODO tests for incorrect input

}