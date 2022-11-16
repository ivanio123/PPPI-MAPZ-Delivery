package com.pppi.novaposhta.entity;

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class DirectionDeliveryTest {

    private final City vinnytsia;
    private final City kyiv;
    private final City zhytomyr;

    public DirectionDeliveryTest() {
        vinnytsia = new City("Vinnytsia", "21012");
        vinnytsia.setId(1L);

        kyiv = new City("Kyiv", "01001-06999");
        kyiv.setId(2L);

        zhytomyr = new City("Zhytomyr", "10000-10031");
        zhytomyr.setId(3L);
    }

    @Test
    @SneakyThrows
    void testClone() {
        DirectionDelivery direction = new DirectionDelivery();
        direction.setId(1L);
        direction.setSenderCity(vinnytsia);
        direction.setReceiverCity(kyiv);

        DirectionDelivery clone = (DirectionDelivery) direction.clone();

        Assert.assertNotSame(direction.getSenderCity(), clone.getSenderCity());
        Assert.assertNotSame(direction.getReceiverCity(), clone.getReceiverCity());
    }
}