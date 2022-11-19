package com.epam.cargo.controller;

import com.epam.cargo.dto.DirectionDeliveryFilterRequest;
import com.epam.cargo.entity.DirectionDelivery;
import com.epam.cargo.service.DirectionDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;
import java.util.stream.Collectors;

@Controller
public class InformationController {

    @Autowired
    private DirectionDeliveryService directionDeliveryService;

    @GetMapping("/")
    public String information(
            Model model,
            @PageableDefault(size = 9, sort = {"senderCity.name"}, direction = Sort.Direction.ASC) Pageable pageable,
            Locale locale,
            DirectionDeliveryFilterRequest filter
    ){
        Page<DirectionDelivery> directionsPage;

        directionsPage = directionDeliveryService.getPage(pageable, locale, filter);

        model.addAttribute("directionsPage", directionsPage);
        model.addAttribute("url", "/");

        Sort.Order order = pageable.getSort().get().collect(Collectors.toList()).get(0);
        model.addAttribute("order", order);

        String lang = locale.getLanguage();
        model.addAttribute("lang", lang);

        model.addAttribute("senderCity", filter.getSenderCityName());
        model.addAttribute("receiverCity", filter.getReceiverCityName());

        return "information";
    }
}
