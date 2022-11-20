package com.pppi.novaposhta.repos;

import com.pppi.novaposhta.entity.DeliveryReceipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
/**
 * Repository of fetching DeliveryReceipt objects from database.
 * @author group2
 * @version 1.0
 * */
public interface DeliveryReceiptRepo extends JpaRepository<DeliveryReceipt, Long> {
    Optional<DeliveryReceipt> findByApplicationId(Long id);

    List<DeliveryReceipt> findAllByCustomerId(Long id);

    Page<DeliveryReceipt> findAllByCustomerId(Long id, Pageable pageable);
}
