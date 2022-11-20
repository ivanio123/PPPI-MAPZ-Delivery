package com.pppi.novaposhta.repos;

import com.pppi.novaposhta.entity.Address;
import com.pppi.novaposhta.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
/**
 * Repository of fetching Addresses objects from database.
 * @author group2
 * @version 1.0
 * */
public interface AddressRepo extends JpaRepository<Address, Long> {
    @Override
    Optional<Address> findById(Long id);

    @Override
    List<Address> findAll();

    Address findByHouseNumberAndCityAndStreet(String houseNumber, City city, String street);
}
