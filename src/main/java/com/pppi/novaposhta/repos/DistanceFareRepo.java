package com.pppi.novaposhta.repos;

import com.pppi.novaposhta.entity.DistanceFare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
/**
 * Repository of fetching DistanceFare objects from database.
 * @author group2
 * @version 1.0
 * */
public interface DistanceFareRepo extends JpaRepository<DistanceFare, Long> {

    @Query(value = "SELECT f FROM DistanceFare f WHERE ?1 BETWEEN f.distanceFrom AND f.distanceTo")
    DistanceFare findFareByDistance(Integer distance);

    @Query(value = " SELECT * FROM distance_fares WHERE price = (SELECT MAX(price) FROM distance_fares)", nativeQuery = true)
    DistanceFare findMaxFare();
}
