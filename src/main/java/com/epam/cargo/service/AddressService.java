package com.epam.cargo.service;

import com.epam.cargo.entity.Address;
import com.epam.cargo.entity.City;
import com.epam.cargo.exception.NoExistingCityException;
import com.epam.cargo.repos.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

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


        if(Objects.isNull(address.getCity()) || Objects.isNull(cityService.findCityById(address.getCity().getId()))){
            throw new NoExistingCityException(Optional.ofNullable(address.getCity()).orElse(new City()), ResourceBundle.getBundle(messages));
        }

        Address foundAddress;
        if (!Objects.isNull(foundAddress = addressRepo.findByHouseNumberAndCityAndStreet(address.getHouseNumber(), address.getCity(), address.getStreet()))){
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
