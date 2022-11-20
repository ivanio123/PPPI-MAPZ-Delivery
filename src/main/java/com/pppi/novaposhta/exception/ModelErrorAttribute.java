package com.pppi.novaposhta.exception;

/**
 * Enum class with common constants of model attribute.<br>
 * The values are model attributes used on markup web pages.<br>
 * @author group2
 * @version 1.0
 * */
public enum ModelErrorAttribute {
    DUPLICATE_PASSWORD("duplicatePasswordErrorMessage"),
    LOGIN("loginErrorMessage"),
    PASSWORD("passwordErrorMessage"),
    NAME("nameErrorMessage"),
    PHONE("phoneErrorMessage"),
    SURNAME("surnameErrorMessage"),
    RECEIVING("receivingDateErrorMessage"),
    CITY_DIRECTION("invalidCityDirectionErrorMessage"),
    PAYING("payingErrorMessage"),
    ABSENT_CITY("noExistingCityMessage");

    private final String attr;

    ModelErrorAttribute(String attr) {
        this.attr = attr;
    }

    public String getAttr() {
        return attr;
    }
}
