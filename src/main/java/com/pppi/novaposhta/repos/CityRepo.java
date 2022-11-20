package com.pppi.novaposhta.repos;

import com.pppi.novaposhta.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
/**
 * Repository of fetching City objects from database.
 * @author group2
 * @version 1.0
 * */
public interface CityRepo extends JpaRepository<City, Long> {
    @Override
    Optional<City> findById(Long id);

    Optional<City> findByZipcode(String zipcode);

    @Override
    List<City> findAll();
}
