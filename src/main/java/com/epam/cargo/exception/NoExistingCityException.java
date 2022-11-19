package com.epam.cargo.exception;

import com.epam.cargo.entity.City;

import java.util.ResourceBundle;

import static com.epam.cargo.exception.WrongInputDataKeysConstants.NO_EXISTING_CITY_KEY_ERROR_MESSAGE;

public class NoExistingCityException extends WrongDataException{

    public NoExistingCityException(){
        super("Your city is absent in our data base!");
    }

    public NoExistingCityException(City city, ResourceBundle bundle) {
        super(buildErrorMessage(city, bundle));
    }

    private static String buildErrorMessage(City city, ResourceBundle bundle) {
        return String.format(bundle.getString(NO_EXISTING_CITY_KEY_ERROR_MESSAGE), city.getName());
    }

    @Override
    public String getModelAttribute() {
        return "noExistingCityMessage";
    }

}
