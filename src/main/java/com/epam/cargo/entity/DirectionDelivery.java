package com.epam.cargo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.text.Collator;
import java.util.Comparator;
import java.util.Objects;

@Entity
@Table(name="directions")
@Getter
@Setter
public class DirectionDelivery implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sender_city_id")
    private City senderCity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="receiver_city_id")
    private City receiverCity;

    @Column(name="distance")
    private Double distance;

    public DirectionDelivery() {
    }

    public DirectionDelivery(City senderCity, City receiverCity, Double distance) {
        this.senderCity = senderCity;
        this.receiverCity = receiverCity;
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DirectionDelivery)) return false;
        DirectionDelivery that = (DirectionDelivery) o;
        return id.equals(that.id) &&
                senderCity.equals(that.senderCity) &&
                receiverCity.equals(that.receiverCity) &&
                distance.equals(that.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, senderCity, receiverCity, distance);
    }

    @Override
    public String toString() {
        return "DirectionDelivery{" +
                "id=" + id +
                ", senderCity=" + senderCity +
                ", receiverCity=" + receiverCity +
                ", distance=" + distance +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        DirectionDelivery clone = (DirectionDelivery) super.clone();
        clone.senderCity = (City) this.senderCity.clone();
        clone.receiverCity = (City) this.receiverCity.clone();
        return clone;
    }

    public static class SenderCityNameComparator implements Comparator<DirectionDelivery> {

        private Collator collator;

        public SenderCityNameComparator(Collator collator) {
            this.collator = collator;
        }

        @Override
        public int compare(DirectionDelivery o1, DirectionDelivery o2) {
            return new City.NameComparator(collator).compare(o1.getSenderCity(), o2.getSenderCity());
        }
    }

    public static class ReceiverCityNameComparator implements Comparator<DirectionDelivery> {

        private final Collator collator;

        public ReceiverCityNameComparator(Collator collator) {
            this.collator = collator;
        }

        @Override
        public int compare(DirectionDelivery o1, DirectionDelivery o2) {
            return new City.NameComparator(collator).compare(o1.getReceiverCity(), o2.getReceiverCity());
        }
    }

    public static class DistanceComparator implements Comparator<DirectionDelivery> {
        @Override
        public int compare(DirectionDelivery o1, DirectionDelivery o2) {
            return o1.getDistance().compareTo(o2.getDistance());
        }
    }

    /**
     * creates a new object from given parameters
     * @return new created DirectionDelivery object
     * */
    public static DirectionDelivery of(Long id, City senderCity, City receiverCity, Double distance) {
        DirectionDelivery direction = new DirectionDelivery();
        direction.setId(id);
        direction.setSenderCity(senderCity);
        direction.setReceiverCity(receiverCity);
        direction.setDistance(distance);
        return direction;
    }
}
