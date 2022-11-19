package com.epam.cargo.repos;

import com.epam.cargo.entity.Address;
import com.epam.cargo.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepo extends JpaRepository<Address, Long> {
    @Override
    Optional<Address> findById(Long id);

    @Override
    List<Address> findAll();               //untested

    Address findByHouseNumberAndCityAndStreet(String houseNumber, City city, String street);
}
