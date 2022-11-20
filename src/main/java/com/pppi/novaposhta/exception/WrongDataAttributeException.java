package com.pppi.novaposhta.exception;

import java.util.ResourceBundle;

/**
 * WrongDataException class implementation of wrong user's input with specified model attribute.<br>
 * @author group2
 * @version 1.0
 * */
public class WrongDataAttributeException extends WrongDataException{

    /**
     * Model error attribute.
     * */
    private final String attributeName;

    /**
     * Constructor with model attribute and key ResourceBundle constant.
     * @param attribute model error attribute
     * @param bundle mean of localization
     * @param keyMessage key of error message in ResourceBundle
     * */
    public WrongDataAttributeException(String attribute, ResourceBundle bundle, String keyMessage){
        super(bundle, keyMessage);
        attributeName = attribute;
    }

    @Override
    public String getModelAttribute() {
        return attributeName;
    }
}
