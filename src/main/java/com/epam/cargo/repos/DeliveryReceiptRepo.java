package com.epam.cargo.repos;

import com.epam.cargo.entity.DeliveryReceipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryReceiptRepo extends JpaRepository<DeliveryReceipt, Long> {
    Optional<DeliveryReceipt> findByApplicationId(Long id);

    List<DeliveryReceipt> findAllByCustomerId(Long id);
}
