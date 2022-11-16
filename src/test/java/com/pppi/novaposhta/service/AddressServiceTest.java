package com.pppi.novaposhta.service;

import com.pppi.novaposhta.entity.Address;
import com.pppi.novaposhta.exception.NoExistingCityException;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

@RunWith(SpringRunner.class)
@SpringBootTest
class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CityService cityService;

    @Test
    @SneakyThrows
    void addAddressAndDeleteThen() {
        Address address = new Address();
        String zipcode = "21012";
        String street = "Pyrogova";
        address.setCity(cityService.findCityByZipCode(zipcode));
        address.setStreet(street);
        address.setHouseNumber("98");

        boolean isAdded = addressService.addAddress(address);

        Assert.assertTrue(isAdded);

        addressService.deleteAddress(address);

        Assert.assertThrows(NoSuchElementException.class, ()->addressService.findAddressById(address.getId()));
    }

    @Test
    void addAddressFailed(){
        Address address = new Address();
        String InvalidZipcode = "invalidZipCode";
        String street = "Kelezka";


        address.setCity(cityService.findCityByZipCode(InvalidZipcode));
        address.setStreet(street);
        address.setHouseNumber("98");

        Assert.assertThrows(NoExistingCityException.class, () -> addressService.addAddress(address));

    }

    @Test
    @SneakyThrows
    void findAddressById(){
        Address address1 = new Address();
        String zipcode = "21012";
        String street = "Pyrogova";
        address1.setCity(cityService.findCityByZipCode(zipcode));
        address1.setStreet(street);
        address1.setHouseNumber("100");

        addressService.addAddress(address1);

        Address address2 = addressService.findAddressById(address1.getId());

        Assert.assertEquals(address1.getId(), address2.getId());
    }
}