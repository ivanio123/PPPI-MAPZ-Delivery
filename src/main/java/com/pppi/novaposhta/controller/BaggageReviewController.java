package com.pppi.novaposhta.controller;

import com.pppi.novaposhta.service.AmountBaggagePerTypeService;
import com.pppi.novaposhta.service.DeliveredBaggageService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
@PreAuthorize("hasAuthority('MANAGER')")
public class BaggageReviewController {

    private final AmountBaggagePerTypeService baggagePerTypeService;

    private final DeliveredBaggageService baggageService;

    private final String AMOUNT_BAGGAGE_PER_TYPE_REPORT = "amountBaggagePerTypeReport";
    private final String PROFIT_BAGGAGE_PER_TYPE_REPORT = "profitBaggagePerTypeReport";

    @GetMapping("/baggage/review")
    public String baggageReviewPage(
            Model model
    ) {
        model.addAttribute(AMOUNT_BAGGAGE_PER_TYPE_REPORT, baggagePerTypeService.getBaggageReport());
        model.addAttribute(PROFIT_BAGGAGE_PER_TYPE_REPORT, baggageService.profitBaggageReport());

        return "baggageReview";
    }
}
