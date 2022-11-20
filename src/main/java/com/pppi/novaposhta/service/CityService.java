package com.pppi.novaposhta.service;

import com.pppi.novaposhta.entity.City;
import com.pppi.novaposhta.repos.CityRepo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for managing City objects.<br>
 * @author Roman Kovalchuk
 * @see City
 * @version 1.0
 * */
@Service
public class CityService {

    @Value("${spring.messages.basename}")
    private String messages;

    @Autowired
    private CityRepo cityRepo;

    /**
     * add City object to database if city with such zipcode is absent
     * @param city city for adding
     * @return true if object was successfully added, otherwise false
     * */
    public boolean addCity(City city){
        if (cityRepo.findByZipcode(city.getZipcode()).isPresent()){
            return false;
        }
        cityRepo.save(city);

        return true;
    }
    /**
     * find city in database according to the given id
     * @param id city identifier in database
     * @return found City object or null if absent
     * */
    public City findCityById(Long id){
        return cityRepo.findById(id).orElse(null);
    }

    /**
     * find city in database according given zipcode
     * @param zipcode post code of City object
     * @return found City object or null if city with given zipcode is absent
     * */
    public City findCityByZipCode(String zipcode) {
        return cityRepo.findByZipcode(zipcode).orElse(null);
    }

    /**
     * localize given city according to given bundle
     * @param city object to localize
     * @param locale locale for localization
     * @return new object localized by needed locale
     * */
    @SneakyThrows
    public City localize(City city, Locale locale){
       return localize(city, ResourceBundle.getBundle(messages, locale));
    }

    /**
     * localize given city according to given bundle
     * @param city object to localize
     * @param bundle mean for localization
     * @return new object localized by needed locale
     * */
    @SneakyThrows
    public City localize(City city, ResourceBundle bundle){
        City localizedCity = (City) city.clone();
        localizedCity.setName(bundle.getString("city."+city.getName()));
        return localizedCity;
    }

    public List<City> findAll(Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle(messages, locale);
        return findAll().stream().map(o -> localize(o, bundle)).collect(Collectors.toList());
    }

    public List<City> findAll(){
        return cityRepo.findAll();
    }

    public List<City> findAll(Locale locale, Sort sort){
        List<City> cities = findAll(locale);
        sortCities(cities, sort, locale);
        return cities;
    }

    //TODO take out logic into parametrized method of some utility class
    private void sortCities(List<City> cities, Sort sort, Locale locale){
        Collator collator = Collator.getInstance(locale);

        List<Sort.Order> orders = sort.get().collect(Collectors.toList());

        Comparator<City> comparator = null;

        ComparatorRecognizer recognizer = new CityService.ComparatorRecognizer(collator);
        for (Sort.Order order:orders) {
            if (Objects.isNull(comparator)){
                comparator = recognizer.getComparator(order);
            }
            else{
                comparator = comparator.thenComparing(recognizer.getComparator(order));
            }
        }

        if (!Objects.isNull(comparator)) {
            cities.sort(comparator);
        }
    }

    //TODO take out class into some utility class, make ComparatorRecognizer interface
    private static class ComparatorRecognizer {
        private final Map<String, Comparator<City>> comparators;

        ComparatorRecognizer(Collator collator){
            comparators = new HashMap<>();
            comparators.put("name", new City.NameComparator(collator));
        }

        private Comparator<City> getComparator(Sort.Order order){
            Comparator<City> cmp = comparators.get(order.getProperty());
            if (!Objects.isNull(cmp) && order.isDescending()){
                cmp = cmp.reversed();
            }
            return cmp;
        }
    }
}
