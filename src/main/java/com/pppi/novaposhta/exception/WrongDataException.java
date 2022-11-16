package com.pppi.novaposhta.exception;

import java.util.ResourceBundle;

abstract public class WrongDataException extends Exception{

    public abstract String getModelAttribute();

    public WrongDataException(String message) {
        super(message);
    }

    public WrongDataException(ResourceBundle bundle, String keyMessage) {
        super(bundle.getString(keyMessage));
    }

}
