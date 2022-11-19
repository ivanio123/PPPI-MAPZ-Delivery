package com.epam.cargo.exception;

import java.util.ResourceBundle;

public class PasswordConfirmationException extends WrongDataException {

    public PasswordConfirmationException(ResourceBundle bundle) {
        super(bundle.getString(WrongInputDataKeysConstants.CONFIRMATION_PASSWORD_FAILED_KEY_ERROR_MESSAGE));
    }

    public String getModelAttribute() {
        return "duplicatePasswordErrorMessage";
    }
}
