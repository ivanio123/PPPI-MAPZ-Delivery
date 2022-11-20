package com.pppi.novaposhta.controller;

import com.pppi.novaposhta.dto.DeliveryApplicationRequest;
import com.pppi.novaposhta.dto.UpdateDeliveryApplicationRequest;
import com.pppi.novaposhta.entity.BaggageType;
import com.pppi.novaposhta.entity.City;
import com.pppi.novaposhta.entity.DeliveryApplication;
import com.pppi.novaposhta.entity.User;
import com.pppi.novaposhta.exception.NoExistingCityException;
import com.pppi.novaposhta.exception.NoExistingDirectionException;
import com.pppi.novaposhta.exception.WrongDataException;
import com.pppi.novaposhta.service.CityService;
import com.pppi.novaposhta.service.DeliveryApplicationService;
import com.pppi.novaposhta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
public class DeliveryApplicationController {

    @Autowired
    private DeliveryApplicationService applicationService;

    @Autowired
    public CityService cityService;

    @Autowired
    public UserService userService;

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
            Model model
    ){

        model.addAttribute("application", application);
        return "application";
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/application/{application}/reject")
    public String rejectApplication(
            @PathVariable DeliveryApplication application,
            @AuthenticationPrincipal User manager,
            Model model
    ){
        applicationService.rejectApplication(application);
        return "redirect:/applications/review";
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/application/{application}/complete")
    public String completeApplication(
        @PathVariable DeliveryApplication application,
        Model model
    ){
        applicationService.completeApplication(application);
        return String.format("redirect:/application/%s", application.getId().toString());
    }

    @GetMapping("/application/{application}/update")
    public String updateApplicationPage(
            @PathVariable DeliveryApplication application,
            @AuthenticationPrincipal User initiator,
            Model model
    ){
        if (!application.getState().equals(DeliveryApplication.State.SUBMITTED)){
            return "redirect:/forbidden";
        }

        if (!initiator.isManager() && !userService.credentialsEquals(application.getCustomer(), initiator)){
            return "redirect:/forbidden";
        }

        model.addAttribute("url", String.format("/application/%d/update", application.getId()));
        model.addAttribute("application", application);
        model.addAttribute("baggageTypes", BaggageType.values());
        Locale locale = LocaleContextHolder.getLocale();
        List<City> cities = cityService.findAll(locale, Sort.by(Sort.Order.by("name")));
        model.addAttribute("cities", cities);

        return "updateApplication";
    }

    @PostMapping("/application/{application}/update")
    public String updateApplication(
        @PathVariable DeliveryApplication application,
        @AuthenticationPrincipal User initiator,
        @Valid UpdateDeliveryApplicationRequest updated,
        BindingResult result,
        Model model
     ){
        if (!result.hasErrors()){
            if (!initiator.isManager() && !userService.credentialsEquals(application.getCustomer(), initiator)){
                return "redirect:/forbidden";
            }
            try {
                applicationService.edit(application, updated);
            } catch (NoExistingCityException | NoExistingDirectionException e) {
                model.addAttribute(e.getModelAttribute(), e.getMessage());
            }
        }
        else {
            Locale locale = LocaleContextHolder.getLocale();
            ResourceBundle bundle = ResourceBundle.getBundle(messages, locale);
            model.mergeAttributes(ControllerUtils.getErrors(result, bundle));
            return String.format("redirect:/application/%s/update", application.getId().toString());
        }
        return String.format("redirect:/application/%s", application.getId().toString());
    }

}
