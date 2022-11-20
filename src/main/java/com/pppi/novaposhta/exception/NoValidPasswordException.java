package com.pppi.novaposhta.exception;


import java.util.Objects;
import java.util.ResourceBundle;


import static com.pppi.novaposhta.exception.WrongInput.NO_FILLED_PASSWORD;
import static com.pppi.novaposhta.exception.WrongInput.NO_VALID_PASSWORD;

/**
 * Exception class in a case of no valid input password.
 * @author group2
 * @version  1.0
 * */
public class NoValidPasswordException extends WrongDataException{

    /**
     * Constructor for localized error message with rejected input password.
     * @since 1.0
     **/
    public NoValidPasswordException(ResourceBundle bundle, String password) {
        super(getErrorMessage(bundle, password));
    }

    private static String getErrorMessage(ResourceBundle bundle, String password) {
        if (Objects.isNull(password) || password.isBlank()){
            return bundle.getString(NO_FILLED_PASSWORD);
        }
        return bundle.getString(NO_VALID_PASSWORD);
    }

    @Override
    public String getModelAttribute() {
        return ModelErrorAttribute.PASSWORD.getAttr();
    }
}
