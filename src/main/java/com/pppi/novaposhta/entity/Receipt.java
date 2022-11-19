package com.pppi.novaposhta.entity;

import java.time.LocalDate;

public interface Receipt {
    Double getTotalPrice();
    LocalDate getFormationDate();
}
