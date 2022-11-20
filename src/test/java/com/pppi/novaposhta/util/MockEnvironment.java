package com.pppi.novaposhta.util;

import com.pppi.novaposhta.entity.DimensionsFare;
import com.pppi.novaposhta.entity.DistanceFare;
import com.pppi.novaposhta.entity.WeightFare;
import com.pppi.novaposhta.repos.DimensionsFareRepo;
import com.pppi.novaposhta.repos.DistanceFareRepo;
import com.pppi.novaposhta.repos.WeightFareRepo;
import org.mockito.Mockito;

public class MockEnvironment {

    public static void mockDimensionsFareRepo(DimensionsFareRepo dimensionsFareRepo, Integer volume, FareDataMock<DimensionsFare> dataMock) {
        Mockito.doReturn(dataMock.fareValueBetweenBy(volume, DimensionsFare::getDimensionsFrom, DimensionsFare::getDimensionsTo))
                .when(dimensionsFareRepo)
                .findFareByVolume(volume);

        Mockito.doReturn(dataMock.maxFare(DimensionsFare::getPrice))
                .when(dimensionsFareRepo)
                .findMaxFare();
    }


    public static void mockDistanceFareRepo(DistanceFareRepo fareRepo, Integer distance, FareDataMock<DistanceFare> dataMock) {
        Mockito.doReturn(dataMock.fareValueBetweenBy(distance, DistanceFare::getDistanceFrom, DistanceFare::getDistanceTo))
                .when(fareRepo)
                .findFareByDistance(distance);

        Mockito.doReturn(dataMock.maxFare(DistanceFare::getPrice))
                .when(fareRepo)
                .findMaxFare();
    }

    public static void mockWeightFareRepo(WeightFareRepo fareRepo, Double weight, FareDataMock<WeightFare> dataMock) {
        Mockito.doReturn(dataMock.fareValueBetweenBy(weight.intValue(), WeightFare::getWeightFrom, WeightFare::getWeightTo))
                .when(fareRepo)
                .findFareByWeight(weight.intValue());

        Mockito.doReturn(dataMock.maxFare(WeightFare::getPrice))
                .when(fareRepo)
                .findMaxFare();
    }

}
