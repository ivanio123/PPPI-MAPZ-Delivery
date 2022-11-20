package com.pppi.novaposhta.controller;

import com.pppi.novaposhta.dto.UserRequest;
import com.pppi.novaposhta.entity.City;
import com.pppi.novaposhta.exception.WrongDataException;
import com.pppi.novaposhta.service.CityService;
import com.pppi.novaposhta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    @Value("${spring.messages.basename}")
    private String messages;

    @GetMapping
    public String registrationPage(
            UserRequest userRequest,
            Model model,

            Locale locale
    ){
        List<City> cities = cityService.findAll(locale, Sort.by(Sort.Order.by("name")));
        model.addAttribute("cities", cities);
        model.addAttribute("userRequest", userRequest);

        return "registration";
    }

    @PostMapping
    public String registration(
            @Valid UserRequest userRequest,
            BindingResult bindingResult,
            Model model,
            Locale locale,
            RedirectAttributes redirectAttributes
    ) {
        if (!bindingResult.hasErrors()) {
            try {
                userService.registerUser(userRequest);
            } catch (WrongDataException e) {
                redirectAttributes.addFlashAttribute("userRequest", userRequest);
                redirectAttributes.addFlashAttribute(e.getModelAttribute(), e.getMessage());
                return "redirect:/registration";
            }
            return "redirect:/login";
        }
        else{
            ResourceBundle bundle = ResourceBundle.getBundle(messages, locale);
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult, bundle);

            errors.forEach(redirectAttributes::addFlashAttribute);
            redirectAttributes.addFlashAttribute("userRequest", userRequest);
            return "redirect:/registration";
        }

    }


}
