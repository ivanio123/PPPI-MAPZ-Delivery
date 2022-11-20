package com.pppi.novaposhta.service;

import com.pppi.novaposhta.entity.WeightFare;
import com.pppi.novaposhta.repos.WeightFareRepo;
import com.pppi.novaposhta.util.CsvFileWeightFareReader;
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
class WeightFareServiceTest {

    private static final String WEIGHT_FARES_TABLE_CASES = "src/test/resources/static/weight_fares_table.csv";

    @Autowired
    private WeightFareService fareService;

    @MockBean
    private WeightFareRepo weightFareRepo;

    private static List<WeightFare> fares;

    @BeforeAll
    public static void init(){
        fares = CsvFileWeightFareReader.readWeightFaresCsv(WEIGHT_FARES_TABLE_CASES);
    }

    private static Stream<Arguments> testGetPriceCases() {
        return Stream.of(
                Arguments.of(0.0, 20.0),
                Arguments.of(0.5, 20.0),
                Arguments.of(0.99, 20.0),
                Arguments.of(1.0, 20.0),
                Arguments.of(2.0, 20.0),
                Arguments.of(2.5, 20.0),
                Arguments.of(3.0, 30.0),
                Arguments.of(9.9, 30.0),
                Arguments.of(10.0, 60.0),
                Arguments.of(20.0, 60.0),
                Arguments.of(30.0, 120.0),
                Arguments.of(50.0, 120.0),
                Arguments.of(100.0, 150.0),
                Arguments.of(150.0, 150.0),
                Arguments.of(199.0, 150.0),
                Arguments.of(200.0, 300.0),
                Arguments.of(300.0, 450.0),
                Arguments.of(350.0, 450.0),
                Arguments.of(400.0, 600.0)
        );
    }

    @ParameterizedTest
    @MethodSource(value = "testGetPriceCases")
    public void testGetPrice(Double weight, Double price){

        FareDataMock<WeightFare> dataMock = new FareDataMock<>(fares);

        MockEnvironment.mockWeightFareRepo(weightFareRepo, weight, dataMock);

        Assertions.assertEquals(price, fareService.getPrice(weight.intValue()));
    }


}