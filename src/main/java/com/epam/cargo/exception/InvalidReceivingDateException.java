package com.epam.cargo.exception;

import java.util.ResourceBundle;

import static com.epam.cargo.exception.WrongInputDataKeysConstants.RECEIVING_DATE_MUST_BE_AFTER_SENDING_KEY_ERROR_MESSAGE;

public class InvalidReceivingDateException extends WrongDataAttributeException{

    public InvalidReceivingDateException(ResourceBundle bundle) {
        super("receivingDateErrorMessage", bundle, RECEIVING_DATE_MUST_BE_AFTER_SENDING_KEY_ERROR_MESSAGE);
    }
}
