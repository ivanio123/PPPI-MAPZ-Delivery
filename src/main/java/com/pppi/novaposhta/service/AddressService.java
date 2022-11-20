package com.pppi.novaposhta.service;

import com.pppi.novaposhta.entity.Address;
import com.pppi.novaposhta.entity.City;
import com.pppi.novaposhta.exception.NoExistingCityException;
import com.pppi.novaposhta.repos.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Service class for managing Address objects.<br>
 * @author group2
 * @see Address
 * @version 1.0
 * */
@Service
public class AddressService {

    @Value("${spring.messages.basename}")
    private String messages;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private CityService cityService;

    /**
     * adding an address to database
     * add given address if it is absent, otherwise assign an id to argument without adding
     * @param address address to write in database
     * @return true if address was added, false if address already was added
     * @throws NoExistingCityException if present city of address is absent in database
     * */
    public boolean addAddress(Address address) throws NoExistingCityException {
        if (Objects.isNull(address)){
            return false;
        }

        City city = address.getCity();
        if(Objects.isNull(city) || Objects.isNull(city.getId()) || Objects.isNull(cityService.findCityById(city.getId()))){
            throw new NoExistingCityException(Optional.ofNullable(city).orElse(new City()), ResourceBundle.getBundle(messages));
        }

        Address foundAddress;
        if (!Objects.isNull(foundAddress = addressRepo.findByHouseNumberAndCityAndStreet(address.getHouseNumber(), city, address.getStreet()))){
            address.setId(foundAddress.getId());
            return false;
        }
        addressRepo.save(address);
        return true;
    }

    public void deleteAddress(Address address){
        addressRepo.delete(address);
    }

    /**
     * takes an Address object from database
     * @param id of Address
     * @return Address object
     * @throws java.util.NoSuchElementException if object is absent in database
     * */
    public Address findAddressById(Long id){
        return addressRepo.findById(id).orElseThrow();
    }

    public Address findByCityAndStreetAndHouseNumber(City city, String street, String houseNumber){
        return addressRepo.findByHouseNumberAndCityAndStreet(houseNumber, city,  street);
    }
}
