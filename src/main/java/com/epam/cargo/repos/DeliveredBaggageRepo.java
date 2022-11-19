package com.epam.cargo.repos;

import com.epam.cargo.entity.DeliveredBaggage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveredBaggageRepo extends JpaRepository<DeliveredBaggage, Long> {

}
