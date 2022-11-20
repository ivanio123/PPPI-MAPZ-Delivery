package com.pppi.novaposhta.exception;

import com.pppi.novaposhta.entity.City;

import java.util.ResourceBundle;


import static com.pppi.novaposhta.exception.ModelErrorAttribute.CITY_DIRECTION;
import static com.pppi.novaposhta.exception.WrongInput.NO_EXISTING_DIRECTION;

/**
 * Exception class in a case of missing direction between two cities.
 * @see City
 * @author group2
 * @version  1.0
 * */
public class NoExistingDirectionException extends WrongDataException{

    public NoExistingDirectionException(City from, City to, ResourceBundle bundle) {
        super(buildErrorMessage(from, to, bundle));
    }

    /**
     * Constructor for localized error message with rejected City objects.
     * @since 1.0
     **/
    private static String buildErrorMessage(City from, City to, ResourceBundle bundle){
        return String.format(bundle.getString(NO_EXISTING_DIRECTION), from.getName(), to.getName());
    }

    @Override
    public String getModelAttribute() {
        return CITY_DIRECTION.getAttr();
    }
}
