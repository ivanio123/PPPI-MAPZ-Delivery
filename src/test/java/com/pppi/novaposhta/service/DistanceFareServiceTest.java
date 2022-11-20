package com.pppi.novaposhta.service;

import com.pppi.novaposhta.entity.DistanceFare;
import com.pppi.novaposhta.repos.DistanceFareRepo;
import com.pppi.novaposhta.util.CsvFilesDistanceFareReader;
import com.pppi.novaposhta.util.FareDataMock;
import com.pppi.novaposhta.util.MockEnvironment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
class DistanceFareServiceTest {

    private static final String DISTANCE_FARES_TABLE_CASES = "src/test/resources/static/distance_fares_table.csv";
    @Autowired
    private DistanceFareService fareService;

    @MockBean
    private DistanceFareRepo fareRepo;

    private static List<DistanceFare> fares;

    @BeforeAll
    public static void init() {
        fares = CsvFilesDistanceFareReader.readDistanceFaresCsv(DISTANCE_FARES_TABLE_CASES);
        fares.forEach(System.out::println);
    }

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
        FareDataMock<DistanceFare> dataMock = new FareDataMock<>(fares);
        MockEnvironment.mockDistanceFareRepo(fareRepo, distance, dataMock);

        Double price = fareService.findFareByDistance(distance).getPrice();
        Assertions.assertEquals(expectedPrice, price);
    }


}