package com.pppi.novaposhta.exception;

import java.util.ResourceBundle;

import static com.pppi.novaposhta.exception.WrongInputDataKeysConstants.OCCUPIED_LOGIN_KEY_ERROR_FORMAT_MESSAGE;

public class OccupiedLoginException extends WrongDataException{

    public OccupiedLoginException(ResourceBundle bundle, String login) {
        super(buildErrorMessage(bundle, login));
    }

    private static String buildErrorMessage(ResourceBundle bundle, String login) {
        return String.format(bundle.getString(OCCUPIED_LOGIN_KEY_ERROR_FORMAT_MESSAGE), login);
    }

    @Override
    public String getModelAttribute() {
        return "loginErrorMessage";
    }
}
