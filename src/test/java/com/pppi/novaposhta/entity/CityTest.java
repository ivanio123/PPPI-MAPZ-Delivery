package com.pppi.novaposhta.entity;

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class CityTest {

    @Test
    @SneakyThrows
    void testClone(){
        City city = new City("Vinnytsia", "21012");
        city.setId(1L);
        City clone = (City)city.clone();

        Assert.assertNotSame(city, clone);
    }
}