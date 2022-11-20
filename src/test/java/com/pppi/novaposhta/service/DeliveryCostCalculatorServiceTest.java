package com.pppi.novaposhta.service;

import com.pppi.novaposhta.dto.DeliveryCostCalculatorRequest;
import com.pppi.novaposhta.dto.DeliveryCostCalculatorResponse;
import com.pppi.novaposhta.dto.DimensionsRequest;
import com.epam.cargo.entity.*;
import com.pppi.novaposhta.entity.*;
import com.pppi.novaposhta.exception.WrongDataException;
import com.epam.cargo.repos.*;
import com.epam.cargo.util.*;
import com.pppi.novaposhta.repos.*;
import com.pppi.novaposhta.util.*;
import org.junit.jupiter.api.Assertions;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeliveryCostCalculatorServiceTest {

    private static final String PATH_TO_CSV_FILES = "src/test/resources/static/";
    private static final String CITIES_CSV = PATH_TO_CSV_FILES + "cities_table.csv";
    private static final String DIRECTIONS_CSV = PATH_TO_CSV_FILES + "directions_table.csv";
    private static final String DISTANCE_FARES_CSV = PATH_TO_CSV_FILES + "distance_fares_table.csv";
    private static final String WEIGHT_FARES_CSV = PATH_TO_CSV_FILES + "weight_fares_table.csv";
    private static final String DIMENSIONS_FARES_CSV = PATH_TO_CSV_FILES + "dimensions_fares_table.csv";

    @Autowired
    private DeliveryCostCalculatorService deliveryCostCalculatorService;

    @MockBean
    private CityRepo cityRepo;

    @MockBean
    private DirectionDeliveryRepo directionDeliveryRepo;

    @MockBean
    private DistanceFareRepo distanceFareRepo;

    @MockBean
    private WeightFareRepo weightFareRepo;

    @MockBean
    private DimensionsFareRepo dimensionsFareRepo;

    private static final Map<String, City> citiesByZipcodes = new HashMap<>();

    private static final Map<Long, City> citiesById = new HashMap<>();
    private static final List<DirectionDelivery> directions = new ArrayList<>();

    private static final List<DistanceFare> distanceFares = CsvFilesDistanceFareReader.readDistanceFaresCsv(DISTANCE_FARES_CSV);
    private static final List<WeightFare> weightFares = CsvFileWeightFareReader.readWeightFaresCsv(WEIGHT_FARES_CSV);
    private static final List<DimensionsFare> dimensionFares = CsvFileDimensionsFareReader.readDimensionsFaresCsv(DIMENSIONS_FARES_CSV);

    private static final FareDataMock<DistanceFare> distanceFareDataMock = new FareDataMock<>(distanceFares);
    private static final FareDataMock<WeightFare> weightFareDataMock = new FareDataMock<>(weightFares);
    private static final FareDataMock<DimensionsFare> dimensionsFareDataMock = new FareDataMock<>(dimensionFares);

    @BeforeAll
    public static void initTestEnvironment(){
        CsvFilesCityReader.readCitiesCsv(CITIES_CSV, citiesById, citiesByZipcodes);
        CsvFilesDirectionDeliveryReader.readDirectionsDeliveryCsv(DIRECTIONS_CSV, citiesById, directions);
    }

    private static Stream<Arguments> calculateCostCases() {

        return Stream.of(
                Arguments.of(
                        DeliveryCostCalculatorRequest.of(3L, 1L, DimensionsRequest.of(30.0, 20.0, 4.0), 3.0),
                        DeliveryCostCalculatorResponse.of(new City.Distance(getCity(CityZipcodesConstants.UMAN_ZIPCODE), getCity(CityZipcodesConstants.VINNYTSIA_ZIPCODE), 160.1, getCitiesByZipcodes(CityZipcodesConstants.UMAN_ZIPCODE, CityZipcodesConstants.VINNYTSIA_ZIPCODE)), 120.0
                        )
                ),
                Arguments.of(
                        DeliveryCostCalculatorRequest.of(2L, 3L, DimensionsRequest.of(100.0, 40.0, 30.0), 50.0),
                        DeliveryCostCalculatorResponse.of(new City.Distance(getCity(CityZipcodesConstants.KYIV_ZIPCODE), getCity(CityZipcodesConstants.UMAN_ZIPCODE), 375.9, getCitiesByZipcodes(CityZipcodesConstants.KYIV_ZIPCODE, CityZipcodesConstants.CHERKASY_ZIPCODE, CityZipcodesConstants.UMAN_ZIPCODE)), 330.0)
                ),
                Arguments.of(
                        DeliveryCostCalculatorRequest.of(9L, 10L, DimensionsRequest.of(130.0, 155.0, 64.0), 200.0),
                        DeliveryCostCalculatorResponse.of(new City.Distance(getCity(CityZipcodesConstants.KHARKIV_ZIPCODE), getCity(CityZipcodesConstants.CHERNIVTSI_ZIPCODE), 1027.7, getCitiesByZipcodes(CityZipcodesConstants.KHARKIV_ZIPCODE, CityZipcodesConstants.POLTAVA_ZIPCODE, CityZipcodesConstants.CHERKASY_ZIPCODE, CityZipcodesConstants.UMAN_ZIPCODE, CityZipcodesConstants.VINNYTSIA_ZIPCODE, CityZipcodesConstants.KHMELNYTSKIY_ZIPCODE, CityZipcodesConstants.KAMIANETS_ZIPCODE, CityZipcodesConstants.CHERNIVTSI_ZIPCODE)), 580.0)
                ),
                Arguments.of(
                        DeliveryCostCalculatorRequest.of(2L, 1L, DimensionsRequest.of(8.0, 3.0, 0.2), 0.01),
                        DeliveryCostCalculatorResponse.of(new City.Distance(getCity(CityZipcodesConstants.KYIV_ZIPCODE), getCity(CityZipcodesConstants.VINNYTSIA_ZIPCODE), 268.6, getCitiesByZipcodes(CityZipcodesConstants.KYIV_ZIPCODE, CityZipcodesConstants.ZHYTOMYR_ZIPCODE, CityZipcodesConstants.VINNYTSIA_ZIPCODE)), 180.0)
                )
        );
    }

    private static List<City> getCitiesByZipcodes(String... zipcodes){
        return Arrays.stream(zipcodes).map(DeliveryCostCalculatorServiceTest::getCity).collect(Collectors.toList());
    }

    private static City getCity(String zipcode) {
        return citiesByZipcodes.get(zipcode);
    }

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

        MockEnvironment.mockDistanceFareRepo(distanceFareRepo, expectedResponse.getDistance().getDistance().intValue(), distanceFareDataMock);
        MockEnvironment.mockWeightFareRepo(weightFareRepo, request.getWeight(), weightFareDataMock);
        MockEnvironment.mockDimensionsFareRepo(dimensionsFareRepo, request.getDimensions().getVolume().intValue(), dimensionsFareDataMock);

        DeliveryCostCalculatorResponse response = deliveryCostCalculatorService.calculateCost(request, Locale.UK);
        Assertions.assertEquals(expectedResponse.getCost(), response.getCost());
        Assertions.assertEquals(expectedResponse.getDistance(), response.getDistance());
    }
    
    //TODO tests for incorrect input

}