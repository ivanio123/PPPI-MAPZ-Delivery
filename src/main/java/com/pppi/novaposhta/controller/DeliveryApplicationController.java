package com.pppi.novaposhta.controller;

import com.pppi.novaposhta.dto.DeliveryApplicationRequest;
import com.pppi.novaposhta.entity.BaggageType;
import com.pppi.novaposhta.entity.DeliveryApplication;
import com.pppi.novaposhta.entity.User;
import com.pppi.novaposhta.exception.WrongDataException;
import com.pppi.novaposhta.service.CityService;
import com.pppi.novaposhta.service.DeliveryApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
public class DeliveryApplicationController {

    @Autowired
    private DeliveryApplicationService applicationService;

    @Autowired
    public CityService cityService;

    @Value("${spring.messages.basename}")
    private String messages;

    @GetMapping("/make_app")
    public String makeApplicationPage(
            @AuthenticationPrincipal User customer,
            DeliveryApplicationRequest applicationRequest,
            BindingResult bindingResult,
            Model model,
            Locale locale
    ){
        if (bindingResult.hasErrors()) {

            ResourceBundle  bundle = ResourceBundle.getBundle(messages, locale);
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult, bundle);
            model.mergeAttributes(errors);

        }

        model.addAttribute("types", BaggageType.values());
        model.addAttribute("cities", cityService.findAll(locale, Sort.by(Sort.Order.by("name"))));

        return "makeDeliveryApplication";
    }

    @PostMapping("/make_app")
    public String sendApplication(
            @AuthenticationPrincipal User customer,
            @Valid  DeliveryApplicationRequest applicationRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model,
            Locale locale
    ){
        boolean successfullySent = false;
        if (!bindingResult.hasErrors()){
            try {
                ResourceBundle bundle = ResourceBundle.getBundle(messages, locale);
                successfullySent = applicationService.sendApplication(customer, applicationRequest, bundle);
                model.addAttribute("result", "application was successfully sent");
            }
            catch (WrongDataException e){
                model.addAttribute(e.getModelAttribute(), e.getMessage());

            }
        }
        else{
            ResourceBundle bundle = ResourceBundle.getBundle(messages, locale);
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult, bundle);
            model.mergeAttributes(errors);
        }
        if (!successfullySent) {
            model.asMap().forEach(redirectAttributes::addFlashAttribute);
            return "redirect:/make_app";
        }
        return "redirect:/profile";
    }

    @GetMapping("/application/{application}")
    public String applicationPage(
            @PathVariable DeliveryApplication application,
            @AuthenticationPrincipal User customer,
            Model model

    ){

        model.addAttribute("application", application);
        model.addAttribute("user", customer);
        return "application";
    }
}
