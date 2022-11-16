package com.pppi.novaposhta.exception;

import java.util.ResourceBundle;

public class SameCityDirectionException extends WrongDataException{

    public SameCityDirectionException(ResourceBundle bundle) {
        super(buildErrorMessage(bundle));
    }

    private static String buildErrorMessage(ResourceBundle bundle) {
        return bundle.getString(WrongInputDataKeysConstants.INVALID_CITY_DIRECTION_SAME_CITIES_KEY_ERROR_MESSAGE);
    }


    @Override
    public String getModelAttribute() {
        return "invalidCityDirectionErrorMessage";
    }
}
