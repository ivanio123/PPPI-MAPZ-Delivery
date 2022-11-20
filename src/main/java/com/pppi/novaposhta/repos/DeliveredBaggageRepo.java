package com.pppi.novaposhta.repos;

import com.pppi.novaposhta.entity.DeliveredBaggage;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repository of fetching DeliveredBaggage objects from database.
 * @author group2
 * @version 1.0
 * */
public interface DeliveredBaggageRepo extends JpaRepository<DeliveredBaggage, Long> {

}
