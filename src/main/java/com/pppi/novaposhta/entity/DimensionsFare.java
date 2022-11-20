package com.pppi.novaposhta.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Entity class of dimensions fare.
 * Used in calculating price of delivery cost.
 * @author group2
 * @version 1.0
 * */
@Entity
@Table(name = "dimensions_fares")
@Getter
@Setter
@Builder
public class DimensionsFare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * lower bound of fare (inclusive)
     * */
    @Column(name = "dimensions_from")
    private Integer dimensionsFrom;

    /**
     * upper bound of fare (inclusive)
     * */
    @Column(name = "dimensions_to")
    private Integer dimensionsTo;

    @Column(name = "price")
    private Double price;

    public DimensionsFare() {
    }

    public DimensionsFare(Long id, Integer dimensionsFrom, Integer dimensionsTo, Double price) {
        this.id = id;
        this.dimensionsFrom = dimensionsFrom;
        this.dimensionsTo = dimensionsTo;
        this.price = price;
    }
}
