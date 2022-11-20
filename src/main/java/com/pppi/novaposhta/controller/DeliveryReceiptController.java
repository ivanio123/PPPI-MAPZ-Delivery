package com.pppi.novaposhta.controller;

import com.pppi.novaposhta.dto.DeliveryReceiptRequest;
import com.pppi.novaposhta.entity.DeliveryApplication;
import com.pppi.novaposhta.entity.DeliveryReceipt;
import com.pppi.novaposhta.entity.User;
import com.pppi.novaposhta.exception.NotEnoughMoneyException;
import com.pppi.novaposhta.exception.WrongDataException;
import com.pppi.novaposhta.service.DeliveryApplicationService;
import com.pppi.novaposhta.service.DeliveryReceiptService;
import com.pppi.novaposhta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

/**
 * Takes requests associated with page of handling delivery receipt.
 * @author group2
 * @version 1.0
 * */
@Controller
public class DeliveryReceiptController {

    @Autowired
    private DeliveryReceiptService receiptService;

    @Autowired
    private DeliveryApplicationService applicationService;

    @Autowired
    private UserService userService;

    @Value("${spring.messages.basename}")
    private String messages;

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/application/{application}/accept")
    public String acceptApplicationPage(
            @PathVariable DeliveryApplication application,
            @AuthenticationPrincipal User manager,
            @RequestParam(name = "price", required = false) Double price,
            Model model
    ){
        if (Objects.isNull(price)){
            price = application.getPrice();
        }

        List<DeliveryReceipt> receipts = receiptService.findAll();

        model.addAttribute("price", price);
        model.addAttribute("url", String.format("/application/%s/accept", application.getId()));
        model.addAttribute("application", application);
        model.addAttribute("customer", application.getCustomer());
        model.addAttribute("manager", manager);
        return "deliveryReceipt";
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/application/{application}/accept")
    public String makeReceipt(
            @PathVariable DeliveryApplication application,
            @AuthenticationPrincipal User manager,
            @Valid DeliveryReceiptRequest receiptRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model,
            Locale locale
    ){
        if(!application.getState().equals(DeliveryApplication.State.SUBMITTED)){
            redirectAttributes.addFlashAttribute("application", application);
            redirectAttributes.addFlashAttribute("applicationId", application.getId());
            return "redirect:/application/confirmation/failed";
        }
        String url = String.format("/application/%s/accept", application.getId());

        boolean successfullyMade = false;
        if(!bindingResult.hasErrors()){
            try {
                successfullyMade = receiptService.makeReceipt(application, manager, receiptRequest);
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

        if (!successfullyMade){
            model.asMap().forEach(redirectAttributes::addFlashAttribute);
            return String.format("redirect:%s", url);
        }

        return "redirect:/applications/review";

    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/application/confirmation/failed")
    public String failedApplicationConfirmation(
            Long applicationId,
            Model model
    ){
        model.addAttribute("url", "/application/confirmation/failed");
        if (!Objects.isNull(applicationId)) {
            model.addAttribute("applicationId", applicationId);
            model.addAttribute("application", applicationService.findById(applicationId));
        }
        return "deliveryApplicationConfirmationFailed";
    }

    @GetMapping("/receipt/{receipt}")
    public String receiptPage(
        @PathVariable DeliveryReceipt receipt,
        @AuthenticationPrincipal User initiator,
        Model model
    ){
        if (!initiator.isManager() && !userService.credentialsEquals(receipt.getCustomer(), initiator)){
            return "redirect:/forbidden";
        }

        model.addAttribute("receipt", receipt);
        model.addAttribute("url", String.format("/receipt/%s", receipt.getId()));
        return "receipt";
    }

    @GetMapping("/receipt/{receipt}/pay")
    public String payReceiptPage(
            @PathVariable DeliveryReceipt receipt,
            @AuthenticationPrincipal User initiator,
            Model model

    ){

        if (!userService.credentialsEquals(receipt.getCustomer(), initiator)){
            return "redirect:/forbidden";
        }

        model.addAttribute("receipt", receipt);
        model.addAttribute("url", String.format("/receipt/%s/pay", receipt.getId()));
        return "payReceipt";
    }


    @PostMapping("/receipt/{receipt}/pay")
    public String payReceipt(
            @PathVariable DeliveryReceipt receipt,
            @AuthenticationPrincipal User user,
            RedirectAttributes redirectAttributes,
            Model model
    ){

        try {
            receiptService.payReceipt(receipt, user);
        } catch (NotEnoughMoneyException e) {
            redirectAttributes.addFlashAttribute(e.getAttribute(), e.getMessage());
            redirectAttributes.addFlashAttribute("rejectedFunds", e.getRejectedFunds());
            redirectAttributes.addFlashAttribute("price", e.getReceipt().getTotalPrice());
            return "redirect:/paying/failed";
        }
        return "redirect:/profile";
    }
}
