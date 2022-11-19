package com.epam.cargo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "weight_fares")
@Getter
@Setter
public class WeightFare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weight_from")
    private Integer weightFrom;

    @Column(name = "weight_to")
    private Integer weightTo;

    @Column(name = "price")
    private Double price;

}
