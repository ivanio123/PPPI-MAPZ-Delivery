package com.pppi.novaposhta.repos;

import com.pppi.novaposhta.entity.City;
import com.pppi.novaposhta.entity.DirectionDelivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
/**
 * Repository of fetching DirectionDelivery objects from database.
 * @author group2
 * @version 1.0
 * */
public interface DirectionDeliveryRepo extends JpaRepository<DirectionDelivery, Long> {
    @Override
    Optional<DirectionDelivery> findById(Long id);

    @Override
    List<DirectionDelivery> findAll();

    Page<DirectionDelivery> findAll(Pageable pageable);

    DirectionDelivery findBySenderCityAndReceiverCity(City senderCity, City receiverCity);
}
