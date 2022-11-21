package com.pppi.novaposhta.service;

import com.pppi.novaposhta.custom.IMonthlyProfit;
import com.pppi.novaposhta.repos.DeliveryReceiptRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfitReporterService {

    private final DeliveryReceiptRepo receiptRepo;

    @Autowired
    public ProfitReporterService(DeliveryReceiptRepo receiptRepo) {
        this.receiptRepo = receiptRepo;
    }

    public List<IMonthlyProfit> monthlyProfit(){
        return receiptRepo.monthlyProfit();
    }
}
