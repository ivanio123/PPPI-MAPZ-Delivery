package com.epam.cargo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "delivered_baggage")
@Getter
@Setter
public class DeliveredBaggage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "volume")
    private Double volume;

    @Enumerated(EnumType.ORDINAL)
    private BaggageType type;

    @Column(name = "description")
    private String description;

}
