package com.epam.cargo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "distance_fares")
@Getter
@Setter
public class DistanceFare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "distance_from")
    private Integer distanceFrom;

    @Column(name = "distance_to")
    private Integer distanceTo;

    @Column(name = "price")
    private Double price;

}
