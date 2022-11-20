package com.pppi.novaposhta.exception;

import java.util.ResourceBundle;


import static com.pppi.novaposhta.exception.WrongInput.OCCUPIED_LOGIN_FORMAT;

/**
 * Exception class in a case of already taken input login during registration.
 * @author group2
 * @version  1.0
 * */
public class OccupiedLoginException extends WrongDataException{

    /**
     * Constructor for localized error message with rejected input login.
     * @since 1.0
     **/
    public OccupiedLoginException(ResourceBundle bundle, String login) {
        super(buildErrorMessage(bundle, login));
    }

    private static String buildErrorMessage(ResourceBundle bundle, String login) {
        return String.format(bundle.getString(OCCUPIED_LOGIN_FORMAT), login);
    }

    @Override
    public String getModelAttribute() {
        return ModelErrorAttribute.LOGIN.getAttr();
    }
}
