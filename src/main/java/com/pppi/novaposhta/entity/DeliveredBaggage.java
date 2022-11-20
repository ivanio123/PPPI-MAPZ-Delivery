package com.pppi.novaposhta.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Represents delivered baggage.
 * Used as a part of delivery application.
 * @author group2
 * @version 1.0
 * */
@Entity
@Table(name = "delivered_baggage")
@Getter
@Setter
@Builder
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

    public DeliveredBaggage() {
    }

    public DeliveredBaggage(Long id, Double weight, Double volume, BaggageType type, String description) {
        this.id = id;
        this.weight = weight;
        this.volume = volume;
        this.type = type;
        this.description = description;
    }
}
