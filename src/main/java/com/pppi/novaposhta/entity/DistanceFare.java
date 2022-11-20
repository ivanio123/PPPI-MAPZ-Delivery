package com.pppi.novaposhta.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Entity class of distance fare.
 * Used in calculating price of delivery cost.
 * @author group2
 * @version 1.0
 * */
@Entity
@Table(name = "distance_fares")
@Getter
@Setter
@Builder
public class DistanceFare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * lower bound of fare (inclusive)
     * */
    @Column(name = "distance_from")
    private Integer distanceFrom;

    /**
     * upper bound of fare (inclusive)
     * */
    @Column(name = "distance_to")
    private Integer distanceTo;

    @Column(name = "price")
    private Double price;

    public DistanceFare() {

    }

    public DistanceFare(Long id, Integer distanceFrom, Integer distanceTo, Double price) {
        this.id = id;
        this.distanceFrom = distanceFrom;
        this.distanceTo = distanceTo;
        this.price = price;
    }
}
