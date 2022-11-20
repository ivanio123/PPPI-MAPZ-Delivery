package com.pppi.novaposhta.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * Entity class for representing address.
 * Used as directly user's address or address of delivering.
 * @author group2
 * @version 1.0
 * */
@Entity
@NoArgsConstructor
@Table(name="addresses")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Address(City city, String street, String houseNumber) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="city_id")
    private City city;

    @Column(name = "street")
    private String street;

    @Column(name="house_number")
    private String houseNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id) &&
                Objects.equals(city, address.city) &&
                Objects.equals(street, address.street) &&
                Objects.equals(houseNumber, address.houseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, street, houseNumber);
    }
}
