package com.pppi.novaposhta.controller;

import com.pppi.novaposhta.service.ProfitReporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@PreAuthorize("hasAuthority('MANAGER')")
public class ProfitReportController {

    private final ProfitReporterService profitReporter;

    @Autowired
    public ProfitReportController(ProfitReporterService profitReporter) {
        this.profitReporter = profitReporter;
    }

    @GetMapping("/report/profit")
    public String monthlyProfitPage(
            Model model
    ){
        model.addAttribute("profit", profitReporter.monthlyProfit());
        return "profit";
    }

}
