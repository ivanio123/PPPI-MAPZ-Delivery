package com.epam.cargo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;
import java.util.function.Supplier;

import static com.epam.cargo.exception.WrongInputDataKeysConstants.NO_POSITIVE_NUMBER_KEY_ERROR_MESSAGE;
import static com.epam.cargo.exception.WrongInputDataKeysConstants.REQUIRED_KEY_ERROR_MESSAGE;

@Getter
@Setter
public class DimensionsRequest {

    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    @Positive(message = NO_POSITIVE_NUMBER_KEY_ERROR_MESSAGE)
    private Double length;

    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    @Positive(message = NO_POSITIVE_NUMBER_KEY_ERROR_MESSAGE)
    private Double width;

    @NotNull(message = REQUIRED_KEY_ERROR_MESSAGE)
    @Positive(message = NO_POSITIVE_NUMBER_KEY_ERROR_MESSAGE)
    private Double height;

    /**
     * calculate the volume according to given dimensions fields: length, width, height
     * @return the volume in cm^3
     * @throws IllegalArgumentException if field is present but is less equal than zero
     * @throws NullPointerException if field wasn't passed and is null
     * */
    public Double getVolume(){
        return  requireValidField(this::getLength, "length") *
                requireValidField(this::getWidth, "width") *
                requireValidField(this::getHeight, "height");
    }

    private Double requireValidField(Supplier<Double> field, String fieldName){
        Double value = field.get();
        Objects.requireNonNull(value, "field " + fieldName + " cannot be null");
        if (value <= 0){
            throw new IllegalArgumentException("field " + fieldName + " cannot be negative or zero");
        }
        return value;
    }

    public static DimensionsRequest of(Double length, Double width, Double height){
        DimensionsRequest request = new DimensionsRequest();
        request.setLength(length);
        request.setWidth(width);
        request.setHeight(height);
        return request;
    }
}
