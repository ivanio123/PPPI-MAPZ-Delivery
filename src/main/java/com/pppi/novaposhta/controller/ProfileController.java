package com.pppi.novaposhta.controller;

import com.pppi.novaposhta.entity.DeliveryApplication;
import com.pppi.novaposhta.entity.User;
import com.pppi.novaposhta.service.DeliveryApplicationService;
import com.pppi.novaposhta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeliveryApplicationService applicationService;

    @GetMapping
    public String profilePage(
            @AuthenticationPrincipal User user,
            @PageableDefault(size = 7, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(name = "activePill", required = false) String activePill,
            Model model,
            Locale locale
    ){

        model.addAttribute("greeting", String.format("Welcome %s", user.getLogin()));

        Page<DeliveryApplication> applications = userService.getApplications(user, pageable);
        model.addAttribute("applications", applications);
        model.addAttribute("user", user);
        model.addAttribute("url", "/profile");
        model.addAttribute("activePill", activePill);
        model.addAttribute("lang", locale.getLanguage());

        return "profile";
    }

}
