package com.epam.cargo.repos;

import com.epam.cargo.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepo extends JpaRepository<City, Long> {
    @Override
    Optional<City> findById(Long id);

    Optional<City> findByZipcode(String zipcode);

    @Override
    List<City> findAll();               //untested
}
