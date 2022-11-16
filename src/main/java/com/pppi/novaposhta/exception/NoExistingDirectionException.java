package com.pppi.novaposhta.exception;

import com.pppi.novaposhta.entity.City;

import java.util.ResourceBundle;

public class NoExistingDirectionException extends WrongDataException{

    public NoExistingDirectionException(City from, City to, ResourceBundle bundle) {
        super(buildErrorMessage(from, to, bundle));
    }

    private static String buildErrorMessage(City from, City to, ResourceBundle bundle){
        return String.format(bundle.getString(WrongInputDataKeysConstants.NO_EXISTING_DIRECTION_KEY_ERROR_MESSAGE), from.getName(), to.getName());
    }

    @Override
    public String getModelAttribute() {
        return "invalidDirectionErrorMessage";
    }
}
