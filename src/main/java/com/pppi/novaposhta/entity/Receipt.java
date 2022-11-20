package com.pppi.novaposhta.entity;

import java.time.LocalDate;

/**
 * Interface to mark all Receipts classes.
 * @author group2
 * @version 1.0
 * */
public interface Receipt {
    Double getTotalPrice();
    LocalDate getFormationDate();
}
