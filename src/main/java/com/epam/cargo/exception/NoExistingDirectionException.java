package com.epam.cargo.exception;

import com.epam.cargo.entity.City;

import java.util.ResourceBundle;

import static com.epam.cargo.exception.WrongInputDataKeysConstants.NO_EXISTING_DIRECTION_KEY_ERROR_MESSAGE;

public class NoExistingDirectionException extends WrongDataException{

    public NoExistingDirectionException(City from, City to, ResourceBundle bundle) {
        super(buildErrorMessage(from, to, bundle));
    }

    private static String buildErrorMessage(City from, City to, ResourceBundle bundle){
        return String.format(bundle.getString(NO_EXISTING_DIRECTION_KEY_ERROR_MESSAGE), from.getName(), to.getName());
    }

    @Override
    public String getModelAttribute() {
        return "invalidDirectionErrorMessage";
    }
}
