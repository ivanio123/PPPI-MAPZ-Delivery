package com.pppi.novaposhta.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.pppi.novaposhta.exception.WrongInputDataKeysConstants.*;

public class ControllerUtils {

    private static final Map<String, Function<FieldError, String>> actionsByCode = new HashMap<>();

    static {
        actionsByCode.put("typeMismatch", getTypeMismatchFunction());
        actionsByCode.put("NotNull", getNotNullFunction());
        actionsByCode.put("NotBlank", getNotBlankFunction());
        actionsByCode.put("Positive", getPositiveFunction());

    }

    private static Function<FieldError, String> getNotBlankFunction() {
        return DefaultMessageSourceResolvable::getDefaultMessage;
    }

    private static Function<FieldError, String> getNotNullFunction() {
        return DefaultMessageSourceResolvable::getDefaultMessage;
    }

    private static Function<FieldError, String> getPositiveFunction() {
        return DefaultMessageSourceResolvable::getDefaultMessage;
    }

    private static Function<FieldError, String> getTypeMismatchFunction() {
        return fieldError -> {
            String rejected = (String)fieldError.getRejectedValue();

            if (isAnyFractionRecord(rejected) && rejected.contains(",")){
                return USE_DOT_INSTEAD_OF_COMMA_KEY_ERROR_MESSAGE;
            }
            return INCORRECT_NUMBER_FORMAT_KEY_ERROR_MESSAGE;
        };
    }

    private static boolean isAnyFractionRecord(String rejected) {
        return rejected.matches("^[0-9]+[.,][0-9]+$");
    }

    /**
     * gives an localized error key message
     * @param bindingResult caught spring validation errors
     * @param bundle localization tool
     * @return localized error message, if error successfully recognized
     * otherwise returns unknown error message
     * */
    static Map<String, String> getErrors(BindingResult bindingResult, ResourceBundle bundle) {
        Collector<FieldError, ?, Map<String, String>> fieldErrorMapCollector = Collectors.toMap(
                fieldError -> fieldError.getField() + "ErrorMessage",
                fieldError -> bundle.getString(getErrorMessageKey(fieldError))
        );
        return bindingResult.getFieldErrors()
                .stream()
                .collect(fieldErrorMapCollector
                );
    }

    private static String getErrorMessageKey(FieldError fieldError) {
        Function<FieldError, String> action = actionsByCode.getOrDefault(fieldError.getCode(), fieldErr -> UNKNOWN_ERROR_KEY_MESSAGE);
        return action.apply(fieldError);
    }

}
