package com.epam.cargo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "dimensions_fares")
@Getter
@Setter
public class DimensionsFare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dimensions_from")
    private Integer dimensionsFrom;

    @Column(name = "dimensions_to")
    private Integer dimensionsTo;

    @Column(name = "price")
    private Double price;
}
