package com.epam.cargo.controller;

import com.epam.cargo.entity.DimensionsFare;
import com.epam.cargo.entity.DistanceFare;
import com.epam.cargo.entity.WeightFare;
import com.epam.cargo.service.DimensionsFareService;
import com.epam.cargo.service.DistanceFareService;
import com.epam.cargo.service.WeightFareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/fares")
public class FaresController {

    @Autowired
    private DistanceFareService distanceFareService;

    @Autowired
    private WeightFareService weightFareService;

    @Autowired
    private DimensionsFareService dimensionsFareService;

    @GetMapping
    public String faresPage(
        Model model
    ){
        List<DistanceFare> distanceFares = distanceFareService.findAllFares();
        List<WeightFare> weightFares = weightFareService.findAllFares();
        List<DimensionsFare> dimensionsFares = dimensionsFareService.findAllFares();

        model.addAttribute("distanceFares", distanceFares);
        model.addAttribute("weightFares", weightFares);
        model.addAttribute("dimensionsFares", dimensionsFares);

        return "fares";
    }
}
