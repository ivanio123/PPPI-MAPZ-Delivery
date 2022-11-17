package com.pppi.novaposhta.controller;

import com.pppi.novaposhta.dto.DeliveryApplicationsReviewFilterRequest;
import com.pppi.novaposhta.entity.DeliveryApplication;
import com.pppi.novaposhta.entity.User;
import com.pppi.novaposhta.service.DeliveryApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Locale;

@Controller
@PreAuthorize("hasAuthority('MANAGER')")
@RequestMapping("/applications/review")
public class DeliveryApplicationsReviewController {

    @Autowired
    private DeliveryApplicationService applicationService;

    @GetMapping
    public String reviewApplications(
            @AuthenticationPrincipal User manager,
            DeliveryApplicationsReviewFilterRequest applicationsRequest,
            Model model,
            Locale locale
    ){
        List<DeliveryApplication> applications = applicationService.findAll(applicationsRequest);
        model.addAttribute("applications", applications);
        return "applicationsReview";
    }
}