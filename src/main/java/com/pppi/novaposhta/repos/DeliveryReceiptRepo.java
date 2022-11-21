package com.pppi.novaposhta.repos;

import com.pppi.novaposhta.custom.IMonthlyProfit;
import com.pppi.novaposhta.entity.DeliveryReceipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    @Query(value = "SELECT " +
            "    CASE EXTRACT (MONTH FROM da.sending_date) " +
            "        WHEN 1 THEN 'January' " +
            "        WHEN 2 THEN 'February' " +
            "        WHEN 3 THEN 'March' " +
            "        WHEN 4 THEN 'April' " +
            "        WHEN 5 THEN 'May' " +
            "        WHEN 6 THEN 'June' " +
            "        WHEN 7 THEN 'July' " +
            "        WHEN 8 THEN 'August' " +
            "        WHEN 9 THEN 'September' " +
            "        WHEN 10 THEN 'October' " +
            "        WHEN 11 THEN 'November' " +
            "        WHEN 12 THEN 'December' " +
            "    END AS month, " +
            "    SUM (dr.total_price) AS profit " +
            "FROM delivery_applications da " +
            "         LEFT JOIN delivery_receipts dr ON da.id = dr.application_id " +
            "WHERE dr.paid " +
            "GROUP BY EXTRACT (MONTH FROM da.sending_date) " +
            "ORDER BY EXTRACT (MONTH FROM da.sending_date);",
            nativeQuery = true)
    List<IMonthlyProfit> monthlyProfit();
}

