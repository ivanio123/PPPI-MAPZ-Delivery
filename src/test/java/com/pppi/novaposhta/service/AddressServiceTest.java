package com.pppi.novaposhta.service;

import com.pppi.novaposhta.entity.Address;
import com.pppi.novaposhta.entity.City;
import com.pppi.novaposhta.exception.NoExistingCityException;
import com.pppi.novaposhta.mock.AddressMockEnvironment;
import com.pppi.novaposhta.mock.CityMockEnvironment;
import com.pppi.novaposhta.repos.AddressRepo;
import com.pppi.novaposhta.repos.CityRepo;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CityService cityService;

    @MockBean
    private AddressRepo addressRepo;

    @MockBean
    private CityRepo cityRepo;

    private CityMockEnvironment cityMockEnvironment = new CityMockEnvironment();
    private AddressMockEnvironment addressMockEnvironment = new AddressMockEnvironment();

    public static Stream<Arguments> correctAddresses() {
        return Stream.of(
            Arguments.of(Address.builder().city(new City("testCity1", "00001")).street("testStreet1").houseNumber("1").build()),
            Arguments.of(Address.builder().city(new City("testCity2", "00002")).street("testStreet2").houseNumber("2").build())
        );
    }

    @BeforeEach
    public void setUp(){
        cityMockEnvironment.mockCityRepoBean(cityRepo);
        addressMockEnvironment.mockAddressRepoBean(addressRepo);
    }

    public static Stream<Arguments> addressesWithMissingCityTestCase() {
        return Stream.of(
                Arguments.of(Address.builder().city(null).street("Chreschatyk").houseNumber("123").build()),
                Arguments.of(Address.builder().city(new City("Kyiv", "12345")).street("Chreschatyk").houseNumber("123").build()),
                Arguments.of(Address.builder().city(new City()).street("Chreschatyk").houseNumber("123").build())
        );
    }

    @ParameterizedTest
    @MethodSource("correctAddresses")
    @SneakyThrows
    void addAddress(Address address) {
        cityService.addCity(address.getCity());
        boolean isAdded = addressService.addAddress(address);

        Assertions.assertTrue(isAdded);
        Assertions.assertEquals(address, addressService.findAddressById(address.getId()));
    }

    @ParameterizedTest
    @MethodSource("addressesWithMissingCityTestCase")
    void addAddressFailed(Address address){
        Assert.assertThrows(NoExistingCityException.class, () -> addressService.addAddress(address));
    }

    @ParameterizedTest
    @MethodSource("correctAddresses")
    @SneakyThrows
    void findAddressById(Address address){
        cityService.addCity(address.getCity());
        addressService.addAddress(address);
        Assertions.assertNotNull(address.getId());
        Assertions.assertEquals(address, addressService.findAddressById(address.getId()));
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("correctAddresses")
    public void findByCityAndStreetAndHouseNumber(Address address){
        cityService.addCity(address.getCity());
        addressService.addAddress(address);
        Assertions.assertNotNull(address.getId());
        Assertions.assertEquals(address, addressService.findByCityAndStreetAndHouseNumber(address.getCity(), address.getStreet(), address.getHouseNumber()));
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("correctAddresses")
    public void deleteAddress(Address address){
        cityService.addCity(address.getCity());
        addressService.addAddress(address);
        Assertions.assertNotNull(address.getId());
        addressService.deleteAddress(address);
        Assertions.assertThrows(NoSuchElementException.class, ()->addressService.findAddressById(address.getId()));
    }

}