package com.pppi.novaposhta.service;

import com.pppi.novaposhta.dto.DirectionDeliveryFilterRequest;
import com.pppi.novaposhta.entity.City;
import com.pppi.novaposhta.entity.DirectionDelivery;
import com.pppi.novaposhta.repos.DirectionDeliveryRepo;
import com.sun.istack.NotNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class DirectionDeliveryService {

    @Value("${spring.messages.basename}")
    private String messages;

    @Autowired
    private DirectionDeliveryRepo directionDeliveryRepo;

    @Autowired
    private CityService cityService;

    public boolean addDirection(City sender, City receiver, Double distance){
        DirectionDelivery direction = new DirectionDelivery(sender, receiver, distance);
        return addDirection(direction);
    }

    public boolean addDirection(DirectionDelivery direction){
        requireNonNullAndSimilarCities(direction);

        DirectionDelivery foundDelivery;
        if (Objects.isNull(
                foundDelivery = directionDeliveryRepo.findBySenderCityAndReceiverCity(
                        direction.getSenderCity(),
                        direction.getReceiverCity()
                ))
        ){
            directionDeliveryRepo.save(direction);
            return true;
        }
        direction.setId(foundDelivery.getId());
        return false;
    }

    public DirectionDelivery findById(Long id) {
        return directionDeliveryRepo.findById(id).orElseThrow();
    }


    public DirectionDelivery findBySenderCityAndReceiverCity(City senderCity, City receiverCity){
        return directionDeliveryRepo.findBySenderCityAndReceiverCity(senderCity, receiverCity);
    }

    public List<DirectionDelivery> findAll(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(messages, locale);

        List<DirectionDelivery> list = directionDeliveryRepo.findAll();
        list = list.stream().map(o->localize(o, bundle)).collect(Collectors.toList());

        return list;
    }

    public Page<DirectionDelivery> findAll(Pageable pageable, Locale locale) {

        Page<DirectionDelivery> page;
        List<DirectionDelivery> directions = findAll(locale);
        page = toPage(directions, pageable, locale);
        return page;
    }

    public Page<DirectionDelivery> getPage(Pageable pageable, Locale locale, DirectionDeliveryFilterRequest filter){
        List<DirectionDelivery> directions = findAll(locale);
        directions = filterDirections(filter, directions);

        Page<DirectionDelivery> page;
        page = toPage(directions, pageable, locale);
        return page;
    }

    /**
     * finds the smallest distance between given cities, according to direction delivery routes in database
     * @return City.Distance object with smallest distance and route as well
     * */
    public City.Distance getDistanceBetweenCities(City cityFrom, City cityTo, Locale locale){
        return CityUtils.getDistance(
                cityService.localize(cityFrom, locale),
                cityService.localize(cityTo, locale),
                findAll(locale)
        );
    }
    /**
     * finds the smallest distance between given cities, according to direction delivery routes in database
     * @return number with represents smaller distance between cities in km
     * if no way exists return Double.POSITIVE_INFINITIVE
     * */
    public Double calculateMinDistance(City cityFrom, City cityTo){
        Locale locale = Locale.UK;
        return CityUtils.getMinDistance( cityService.localize(cityFrom, locale),
                cityService.localize(cityTo, locale),
                findAll(locale)
        );
    }

    @NotNull
    private List<DirectionDelivery> filterDirections(DirectionDeliveryFilterRequest filter, List<DirectionDelivery> directions) {
        return directions.stream()
                .filter(
                        getDirectionDeliveryPredicate(
                                filter::getSenderCityName, DirectionDelivery::getSenderCity)
                                .and(getDirectionDeliveryPredicate(filter::getReceiverCityName, DirectionDelivery::getReceiverCity))
                ).collect(Collectors.toList());
    }

    @NotNull
    private Predicate<DirectionDelivery> getDirectionDeliveryPredicate(Supplier<String> getFilteredName, Function<DirectionDelivery, City> getCity) {
        return directionDelivery -> {
            String filteredName = getFilteredName.get();
            Pattern pattern = Pattern.compile("^" + Optional.ofNullable(filteredName).orElse(""), Pattern.CASE_INSENSITIVE);
            String name = getCity.apply(directionDelivery).getName();
            Matcher matcher = pattern.matcher(name);
            return matcher.find();
        };
    }

    private Page<DirectionDelivery> toPage(List<DirectionDelivery> directions, Pageable pageable, Locale locale) {
        if(pageable.getPageNumber()*pageable.getPageSize() > directions.size()){
            pageable = pageable.withPage(0);
        }
        Sort sort = pageable.getSort();
        sortList(directions, sort, Collator.getInstance(locale));

        int start = (int)pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), directions.size());
        if (start > end){
            return new PageImpl<>(Collections.emptyList(), pageable, directions.size());
        }
        return new PageImpl<>(directions.subList(start, end), pageable, directions.size());
    }

    private void sortList(List<DirectionDelivery> list, Sort sort, Collator collator) {

        List<Sort.Order> orders = sort.get().collect(Collectors.toList());

        Comparator<DirectionDelivery> comparator = null;

        ComparatorRecognizer recognizer = new ComparatorRecognizer(collator);
        for (Sort.Order order:orders) {
            if (Objects.isNull(comparator)){
                comparator = recognizer.getComparator(order);
            }
            else{
                comparator = comparator.thenComparing(recognizer.getComparator(order));
            }
        }

        if (!Objects.isNull(comparator)) {
            list.sort(comparator);
        }
    }

    /**
     * localize given direction according to given locale
     * @param direction object to localize
     * @param locale locale for localization
     * @return new object localized by needed locale
     * */
    @SneakyThrows
    public DirectionDelivery localize(DirectionDelivery direction, Locale locale){
        return localize(direction, ResourceBundle.getBundle(messages, locale));
    }

    /**
     * localize given direction according to given bundle
     * @param direction object to localize
     * @param bundle mean for localization
     * @return new object localized by needed locale
     * */
    @SneakyThrows
    public DirectionDelivery localize(DirectionDelivery direction, ResourceBundle bundle){

        DirectionDelivery localizedDirection = (DirectionDelivery) direction.clone();
        localizedDirection.setSenderCity(cityService.localize(localizedDirection.getSenderCity(), bundle));
        localizedDirection.setReceiverCity(cityService.localize(localizedDirection.getReceiverCity(), bundle));
        return localizedDirection;
    }


    private static class ComparatorRecognizer {

        private final Map<String, Comparator<DirectionDelivery>> comparators;

        ComparatorRecognizer(Collator collator){
            comparators = new HashMap<>();
            comparators.put("senderCity.name", new DirectionDelivery.SenderCityNameComparator(collator));
            comparators.put("receiverCity.name", new DirectionDelivery.ReceiverCityNameComparator(collator));
            comparators.put("distance", new DirectionDelivery.DistanceComparator());
        }

        private Comparator<DirectionDelivery> getComparator(Sort.Order order){
            Comparator<DirectionDelivery> cmp = comparators.get(order.getProperty());
            if (!Objects.isNull(cmp) && order.isDescending()){
                cmp = cmp.reversed();
            }
            return cmp;
        }
    }

    public void deleteDirection(DirectionDelivery direction) {
        directionDeliveryRepo.delete(direction);
    }

    private void requireNonNullAndSimilarCities(DirectionDelivery direction){
        City city1 = Optional.ofNullable(direction.getSenderCity()).orElseThrow();
        City city2 = Optional.ofNullable(direction.getReceiverCity()).orElseThrow();
        if (city1.equals(city2)){
            throw new IllegalArgumentException();
        }
    }

}
