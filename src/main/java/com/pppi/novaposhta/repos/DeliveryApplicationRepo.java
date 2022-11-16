package com.pppi.novaposhta.repos;

import com.pppi.novaposhta.entity.DeliveryApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryApplicationRepo extends JpaRepository<DeliveryApplication, Long> {

    List<DeliveryApplication> findAllByUserId(Long userId);

    Page<DeliveryApplication> findAllByUserId(Long userId, Pageable pageable);
}
