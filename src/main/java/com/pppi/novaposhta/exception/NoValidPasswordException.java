package com.pppi.novaposhta.exception;


import java.util.Objects;
import java.util.ResourceBundle;

public class NoValidPasswordException extends WrongDataException{

    public NoValidPasswordException(ResourceBundle bundle, String password) {
        super(getErrorMessage(bundle, password));
    }

    private static String getErrorMessage(ResourceBundle bundle, String password) {
        if (Objects.isNull(password) || password.isBlank()){
            return bundle.getString(WrongInputDataKeysConstants.NO_FILLED_PASSWORD_KEY_ERROR_MESSAGE);
        }
        return bundle.getString(WrongInputDataKeysConstants.NO_VALID_PASSWORD_KEY_ERROR_MESSAGE);
    }

    @Override
    public String getModelAttribute() {
        return "passwordErrorMessage";
    }
}
