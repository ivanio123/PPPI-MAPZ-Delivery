package com.pppi.novaposhta.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Entity class of weight fare.
 * Used in calculating price of delivery cost.
 * @author group2
 * @version 1.0
 * */
@Entity
@Table(name = "weight_fares")
@Getter
@Setter
@Builder
public class WeightFare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * lower bound of fare (inclusive)
     * */
    @Column(name = "weight_from")
    private Integer weightFrom;

    /**
     * upper bound of fare (inclusive)
     * */
    @Column(name = "weight_to")
    private Integer weightTo;

    @Column(name = "price")
    private Double price;

    public WeightFare() {
    }

    public WeightFare(Long id, Integer weightFrom, Integer weightTo, Double price) {
        this.id = id;
        this.weightFrom = weightFrom;
        this.weightTo = weightTo;
        this.price = price;
    }
}
