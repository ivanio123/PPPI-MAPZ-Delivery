package com.pppi.novaposhta.exception;

import java.util.Objects;
import java.util.ResourceBundle;

public class NoValidLoginException extends WrongDataException{

    public NoValidLoginException(ResourceBundle bundle, String login) {
        super(getErrorMessage(bundle, login));
    }

    private static String getErrorMessage(ResourceBundle bundle, String login) {
        if (Objects.isNull(login) || login.isBlank()){
            return bundle.getString(WrongInputDataKeysConstants.NO_FILLED_LOGIN_KEY_ERROR_MESSAGE);
        }
        return bundle.getString(WrongInputDataKeysConstants.NO_VALID_LOGIN_KEY_ERROR_MESSAGE);
    }

    @Override
    public String getModelAttribute() {
        return "loginErrorMessage";
    }
}
