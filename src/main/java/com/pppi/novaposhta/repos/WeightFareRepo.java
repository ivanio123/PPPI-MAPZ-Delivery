package com.pppi.novaposhta.repos;

import com.pppi.novaposhta.entity.WeightFare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WeightFareRepo extends JpaRepository<WeightFare, Long> {
    @Query(value = "SELECT f FROM WeightFare f WHERE ?1 BETWEEN f.weightFrom AND f.weightTo")
    WeightFare findFareByWeight(Integer weight);

    @Query(value = " SELECT * FROM weight_fares WHERE price = (SELECT MAX(price) FROM weight_fares)", nativeQuery = true)
    WeightFare findMaxFare();
}
