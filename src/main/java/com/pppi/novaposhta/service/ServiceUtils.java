package com.pppi.novaposhta.service;

import com.pppi.novaposhta.dto.AddressRequest;
import com.pppi.novaposhta.dto.DeliveredBaggageRequest;
import com.pppi.novaposhta.dto.DeliveryApplicationRequest;
import com.epam.cargo.entity.*;
import com.pppi.novaposhta.exception.InvalidReceivingDateException;
import com.pppi.novaposhta.exception.NoExistingCityException;
import com.pppi.novaposhta.exception.WrongDataException;
import com.pppi.novaposhta.entity.*;

import java.util.Objects;
import java.util.ResourceBundle;

public class ServiceUtils {

    static DeliveredBaggage createDeliveredBaggage(DeliveredBaggageRequest request){
        Objects.requireNonNull(request);

        DeliveredBaggage object = new DeliveredBaggage();

        object.setDescription(request.getDescription());
        object.setType(Objects.requireNonNull(request.getType()));
        object.setVolume(Objects.requireNonNull(request.getVolume()));
        object.setWeight(Objects.requireNonNull(request.getWeight()));

        return object;
    }

    /**
     * makes an object according to dto request object
     * @param customer owner of application
     * @param request web dto request object
     * @param cityService service of City entity
     * @return the object with initialized fields
     * @throws NoExistingCityException if any given city isn't present in the database
     * @throws NullPointerException if any argument is null
     * */
    static DeliveryApplication createDeliveryApplication(User customer, DeliveryApplicationRequest request, CityService cityService, ResourceBundle bundle) throws WrongDataException {
        Objects.requireNonNull(request);
        Objects.requireNonNull(cityService);

        DeliveryApplication object = new DeliveryApplication();

        object.setCustomer(Objects.requireNonNull(customer));

        object.setDeliveredBaggage(createDeliveredBaggage(request.getDeliveredBaggageRequest()));

        Address senderAddress = createAddress(request.getSenderAddress(), cityService);
        Address receiverAddress = createAddress(request.getReceiverAddress(), cityService);

        requireDifferentAddresses(senderAddress, receiverAddress);

        object.setSenderAddress(senderAddress);
        object.setReceiverAddress(receiverAddress);

        object.setSendingDate(Objects.requireNonNull(request.getSendingDate()));
        object.setReceivingDate(Objects.requireNonNull(request.getReceivingDate()));

        requireValidDates(object, bundle);

        object.setState(DeliveryApplication.State.SUBMITTED);
        return object;
    }

    private static void requireValidDates(DeliveryApplication object, ResourceBundle bundle) throws InvalidReceivingDateException {
        if (object.getSendingDate().isAfter(object.getReceivingDate())){
            throw new InvalidReceivingDateException(bundle);
        }
    }

    private static void requireDifferentAddresses(Address senderAddress, Address receiverAddress) {
        if (senderAddress.equals(receiverAddress)){
            throw new IllegalArgumentException("Sender and Receiver address mustn't be equal!");
        }
    }

    static Address createAddress(AddressRequest request, CityService cityService) throws NoExistingCityException {
        Objects.requireNonNull(request);

        Address object = new Address();
        object.setCity(requireExistingCity(request, cityService));
        object.setStreet(request.getStreetName());
        object.setHouseNumber(request.getHouseNumber());

        return object;
    }


    private static City requireExistingCity(AddressRequest request, CityService cityService) throws NoExistingCityException {
        City city = cityService.findCityById(request.getCityId());
        if (Objects.isNull(city)){
            throw new NoExistingCityException();
        }
        return city;
    }

    static void requireExistingUser(User user, UserService userService){
        if (Objects.isNull(userService.findUserByLogin(user.getLogin()))){
            throw new IllegalArgumentException("User " + user + " must be present in database");
        }
    }
}
