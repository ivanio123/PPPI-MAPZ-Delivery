package com.epam.cargo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "delivery_receipts")
@Getter
@Setter
public class DeliveryReceipt implements Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "application_id")
    private DeliveryApplication application;

    @OneToOne
    @JoinColumn(name = "customer_user_id")
    private User customer;

    @OneToOne
    @JoinColumn(name = "manager_user_id")
    private User manager;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "formation_date", nullable = false)
    private LocalDate formationDate;

    @Column(name = "paid", nullable = false)
    private Boolean paid;

}
