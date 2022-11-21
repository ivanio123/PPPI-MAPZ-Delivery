package com.pppi.novaposhta.repos;

import com.pppi.novaposhta.custom.AmountBaggagePerType;
import com.pppi.novaposhta.custom.IProfitBaggagePerType;
import com.pppi.novaposhta.entity.DeliveredBaggage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository of fetching DeliveredBaggage objects from database.
 * @author group2
 * @version 1.0
 * */
public interface DeliveredBaggageRepo extends JpaRepository<DeliveredBaggage, Long> {

    @Query("SELECT " +
            "new com.pppi.novaposhta.custom.AmountBaggagePerType(" +
            "db.type, " +
            "COUNT(db.type)) " +
            "FROM DeliveryReceipt dr " +
            "LEFT JOIN dr.application da " +
            "LEFT JOIN da.deliveredBaggage db " +
            "WHERE dr.paid = true " +
            "GROUP BY db.type")
    List<AmountBaggagePerType> countBaggagePerType();

    @Query(value =
            "SELECT " +
                    "    b.type, " +
                    "    COALESCE(SUM(dr.total_price), 0) AS profit " +
                    "FROM delivered_baggage b " +
                    "LEFT JOIN delivery_applications da ON b.id = da.baggage_id " +
                    "LEFT JOIN delivery_receipts dr ON da.id = dr.application_id AND dr.paid " +
                    "GROUP BY b.type " +
                    "ORDER BY profit DESC;",
            nativeQuery = true)
    List<IProfitBaggagePerType> profitBaggagePerType();

}
