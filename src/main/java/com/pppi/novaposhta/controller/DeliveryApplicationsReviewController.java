package com.pppi.novaposhta.controller;

import com.pppi.novaposhta.dto.DeliveryApplicationsReviewFilterRequest;
import com.pppi.novaposhta.entity.BaggageType;
import com.pppi.novaposhta.entity.DeliveryApplication;
import com.pppi.novaposhta.entity.User;
import com.pppi.novaposhta.service.CityService;
import com.pppi.novaposhta.service.DeliveryApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

/**
 * Takes requests associated with page of handling delivery applications review.
 * @author group2
 * @version 1.0
 * */
@Controller
@PreAuthorize("hasAuthority('MANAGER')")
@RequestMapping("/applications/review")
public class DeliveryApplicationsReviewController {

    @Autowired
    private DeliveryApplicationService applicationService;

    @Autowired
    private CityService cityService;

    @GetMapping
    public String reviewApplications(
            @AuthenticationPrincipal User manager,
            DeliveryApplicationsReviewFilterRequest applicationsRequest,
            @PageableDefault(size = 6, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
            Model model,
            Locale locale
    ){
        Page<DeliveryApplication> applications = applicationService.getPage(applicationsRequest, pageable);
        model.addAttribute("applicationsPage", applications);
        model.addAttribute("states", DeliveryApplication.State.values());
        model.addAttribute("types", BaggageType.values());
        model.addAttribute("cities", cityService.findAll(locale));
        model.addAttribute("url", "/applications/review");
        model.addAttribute("lang", locale.getLanguage());

        return "applicationsReview";
    }
}
