package com.pppi.novaposhta.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "delivery_applications")
@Getter
@Setter
public class DeliveryApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User customer;

    @Column(name="user_id", insertable = false, updatable = false)
    private Long userId;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sender_address_id")
    private Address senderAddress;

    @OneToOne
    @JoinColumn(name="receiver_address_id")
    private Address receiverAddress;

    @OneToOne
    @JoinColumn(name="baggageId")
    private DeliveredBaggage deliveredBaggage;

    @Column(name = "sending_date")
    private LocalDate sendingDate;

    @Column(name = "receiving_date")
    private LocalDate receivingDate;

    @Enumerated(EnumType.ORDINAL)
    private State state;

    @Column(name = "price")
    private Double price;

    public enum State{
        SUBMITTED, CONFIRMED, COMPLETED, CANCELED
    }
}
