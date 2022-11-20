package com.pppi.novaposhta.repos;

import com.pppi.novaposhta.entity.DeliveryApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * Repository of fetching DeliveredApplication objects from database.
 * @author group2
 * @version 1.0
 * */
public interface DeliveryApplicationRepo extends JpaRepository<DeliveryApplication, Long> {

    List<DeliveryApplication> findAllByUserId(Long userId);

    Page<DeliveryApplication> findAllByUserId(Long userId, Pageable pageable);
}
