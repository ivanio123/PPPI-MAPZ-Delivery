package com.pppi.novaposhta.service;

import com.pppi.novaposhta.dto.DeliveryApplicationRequest;
import com.pppi.novaposhta.dto.DeliveryApplicationsReviewFilterRequest;
import com.pppi.novaposhta.entity.DeliveredBaggage;
import com.pppi.novaposhta.entity.DeliveryApplication;
import com.pppi.novaposhta.entity.User;
import com.pppi.novaposhta.exception.NoExistingCityException;
import com.pppi.novaposhta.exception.NoExistingDirectionException;
import com.pppi.novaposhta.exception.WrongDataException;
import com.pppi.novaposhta.repos.DeliveryApplicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class DeliveryApplicationService {

    @Autowired
    private DeliveryApplicationRepo deliveryApplicationRepo;

    @Autowired
    private CityService cityService;

    @Autowired
    private DeliveredBaggageService deliveredBaggageService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private DeliveryCostCalculatorService costCalculatorService;

    public boolean sendApplication(User customer, DeliveryApplicationRequest request, ResourceBundle bundle) throws WrongDataException {
        Objects.requireNonNull(customer, "Customer object cannot be null");
        Objects.requireNonNull(request, "DeliveryApplicationRequest object cannot be null");

        DeliveryApplication application = ServiceUtils.createDeliveryApplication(customer, request, cityService, bundle);
        application.setPrice(calculatePrice(application));
        return saveApplication(application);

    }

    private Double calculatePrice(DeliveryApplication application) throws NoExistingDirectionException {
        Double distanceCost = costCalculatorService.calculateDistanceCost(application.getSenderAddress(), application.getReceiverAddress());
        DeliveredBaggage deliveredBaggage = application.getDeliveredBaggage();
        Double weightCost = costCalculatorService.calculateWeightCost(deliveredBaggage.getWeight());
        Double dimensionsCost = costCalculatorService.calculateDimensionsCost(deliveredBaggage.getVolume());
        return distanceCost + weightCost + dimensionsCost;
    }

    public boolean saveApplication(DeliveryApplication application) throws NoExistingCityException {
        if (Objects.isNull(application)){
            return false;
        }
        ServiceUtils.requireExistingUser(application.getCustomer(), userService);

        deliveredBaggageService.addBaggage(application.getDeliveredBaggage());
        addressService.addAddress(application.getSenderAddress());
        addressService.addAddress(application.getReceiverAddress());
        deliveryApplicationRepo.save(application);

        return true;
    }

    /**
     * finds application according to the given id
     * @param id unique identifier of application in the database
     * @return found DeliveryApplication object, if no objects are found returns null
     * */
   public DeliveryApplication findById(Long id){
        return deliveryApplicationRepo.findById(id).orElse(null);
   }

    public List<DeliveryApplication> findAll() {
        return deliveryApplicationRepo.findAll();
    }

    public Page<DeliveryApplication> findAllByUserId(Long id, Pageable pageable) {
        return deliveryApplicationRepo.findAllByUserId(id, pageable);
    }

    public List<DeliveryApplication> findAllByUserId(Long id) {
        return deliveryApplicationRepo.findAllByUserId(id);
    }

    public List<DeliveryApplication> findAll(DeliveryApplicationsReviewFilterRequest filter){
        List<DeliveryApplication> applications = findAll();
        applications = applications.stream()
                .filter(
                        statePredicate(filter)
                                .and(baggageTypePredicate(filter))
                                .and(senderCityPredicate(filter))
                                .and(receiverCityPredicate(filter))
                                .and(sendingDatePredicate(filter))
                                .and(receivingDatePredicate(filter))
                )
                .collect(Collectors.toList());

        return applications;
    }

    private Predicate<? super DeliveryApplication> receivingDatePredicate(DeliveryApplicationsReviewFilterRequest filter) {
        return a -> Objects.isNull(filter.getReceivingDate()) || a.getReceivingDate().equals(filter.getReceivingDate());
    }

    private Predicate<? super DeliveryApplication> sendingDatePredicate(DeliveryApplicationsReviewFilterRequest filter) {
        return a -> Objects.isNull(filter.getSendingDate()) || a.getSendingDate().equals(filter.getSendingDate());
    }

    private Predicate<? super DeliveryApplication> senderCityPredicate(DeliveryApplicationsReviewFilterRequest filter) {
        return a -> Objects.isNull(filter.getCityFromId()) || a.getSenderAddress().getCity().getId().equals(filter.getCityFromId());
    }

    private Predicate<? super DeliveryApplication> receiverCityPredicate(DeliveryApplicationsReviewFilterRequest filter) {
        return a -> Objects.isNull(filter.getCityToId()) || a.getReceiverAddress().getCity().getId().equals(filter.getCityToId());
    }

    private Predicate<DeliveryApplication> baggageTypePredicate(DeliveryApplicationsReviewFilterRequest filter) {
        return a -> Objects.isNull(filter.getBaggageType()) || a.getDeliveredBaggage().getType().equals(filter.getBaggageType());
    }


    private Predicate<DeliveryApplication> statePredicate(DeliveryApplicationsReviewFilterRequest filter) {
        return a -> Objects.isNull(filter.getApplicationState()) || a.getState().equals(filter.getApplicationState());
    }

    public Page<DeliveryApplication> getPage(DeliveryApplicationsReviewFilterRequest applicationsRequest, Pageable pageable) {
        List<DeliveryApplication> list = findAll(applicationsRequest);
        return ServiceUtils.toPage(list, pageable, new DeliveryApplicationComparatorRecognizer());
    }

    public void rejectApplication(DeliveryApplication application) {
       application.setState(DeliveryApplication.State.REJECTED);
       deliveryApplicationRepo.save(application);
    }

    private static class DeliveryApplicationComparatorRecognizer implements ServiceUtils.ComparatorRecognizer<DeliveryApplication> {

        private final Map<String, Comparator<DeliveryApplication>> comparators;

        DeliveryApplicationComparatorRecognizer(){
            comparators = new HashMap<>();
            comparators.put("id", Comparator.comparing(DeliveryApplication::getId, Long::compareTo));
        }

        @Override
        public Comparator<DeliveryApplication> getComparator(Sort.Order order) {
            Comparator<DeliveryApplication> cmp = comparators.get(order.getProperty());
            if (!Objects.isNull(cmp) && order.isDescending()){
                cmp = cmp.reversed();
            }
            return cmp;
        }
    }
}
