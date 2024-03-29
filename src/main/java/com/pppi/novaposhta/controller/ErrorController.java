package com.pppi.novaposhta.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;
import java.util.ResourceBundle;

import static com.pppi.novaposhta.exception.NotEnoughMoneyException.TRANSACTION_FAILED_NOT_ENOUGH_MONEY_KEY_ERROR_MESSAGE_FORMAT;
import static com.pppi.novaposhta.exception.PayingException.CURRENCY_UAH_KEY;

/**
 * Takes requests associated with error page.
 * @author group2
 * @version 1.0
 * */
@Controller
public class ErrorController {

    protected static final String ERROR_MESSAGE = "errorMessage";

    @Value("${spring.messages.basename}")
    private String messages;

    @GetMapping("/forbidden")
    public String forbidden(Model model){
        model.addAttribute("url", "/forbidden");
        return "forbidden";
    }

    @GetMapping("/error_page")
    public String errorPage(
            @RequestParam(name = ERROR_MESSAGE, required = false) String message,
            Model model
    ){
        if (Objects.nonNull(message)){
            model.addAttribute(ERROR_MESSAGE, message);
        }
        return "errorPage";
    }

    @GetMapping("/paying/failed")
    public String payingFailed(
            Model model,
            @RequestParam(name="rejectedFunds", required = false) Double rejected,
            @RequestParam(name="price", required = false) Double price
    ){

        model.addAttribute("url", "/paying/failed");
        if(!Objects.isNull(rejected) && !Objects.isNull(price)){
            model.addAttribute("rejectedFunds", rejected);
            model.addAttribute("price", price);
            ResourceBundle bundle = ResourceBundle.getBundle(messages, LocaleContextHolder.getLocale());
            Object payingErrorMessage = buildErrorMessage(rejected, price, bundle);
            model.addAttribute("payingErrorMessage", payingErrorMessage);
        }
        return "payingFailed";
    }

    private static String buildErrorMessage(Double rejectedFunds, Double price, ResourceBundle bundle) {
        String format = bundle.getString(TRANSACTION_FAILED_NOT_ENOUGH_MONEY_KEY_ERROR_MESSAGE_FORMAT);
        String uah = bundle.getString(CURRENCY_UAH_KEY);
        return String.format(format, rejectedFunds.toString() + " " + uah, price.toString() + " " + uah);
    }
}
