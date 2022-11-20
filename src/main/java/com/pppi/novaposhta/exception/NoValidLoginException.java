package com.pppi.novaposhta.exception;

import java.util.Objects;
import java.util.ResourceBundle;


import static com.pppi.novaposhta.exception.WrongInput.NO_FILLED_LOGIN;
import static com.pppi.novaposhta.exception.WrongInput.NO_VALID_LOGIN;

/**
 * Exception class in a case of no valid input login.
 * @author group2
 * @version  1.0
 * */
public class NoValidLoginException extends WrongDataException{

    /**
     * Constructor for localized error message with rejected input login.
     * @since 1.0
     **/
    public NoValidLoginException(ResourceBundle bundle, String login) {
        super(getErrorMessage(bundle, login));
    }

    private static String getErrorMessage(ResourceBundle bundle, String login) {
        if (Objects.isNull(login) || login.isBlank()){
            return bundle.getString(NO_FILLED_LOGIN);
        }
        return bundle.getString(NO_VALID_LOGIN);
    }

    @Override
    public String getModelAttribute() {
        return ModelErrorAttribute.LOGIN.getAttr();
    }
}
