package com.pppi.novaposhta.mock;

import com.pppi.novaposhta.entity.City;
import com.pppi.novaposhta.repos.CityRepo;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;

/**
 * Designed specially for mocking city db repositories
 * Assign generated id for saving object if id is missing, or not exist in locale storage
 * @author Roman Kovalchuk
 * @since 20.02.2022
 * */
public class CityMockEnvironment {

    private Long currentId = 1L;

    private Map<Long, City> cityStorageById = new HashMap<>();

    public void mockFindById(CityRepo repo){
        Mockito.when(repo.findById(any(Long.class)))
                .thenAnswer(findByIdAnswer());
    }

    public void mockFindByZipcode(CityRepo repo){
        Mockito.when(repo.findByZipcode(any(String.class)))
                .thenAnswer(findByZipcodeAnswer());
    }

    public void mockFindAll(CityRepo repo){
        Mockito.when(repo.findAll())
                .thenAnswer(findAllAnswer());
    }

    public void mockSave(CityRepo repo){
        Mockito.when(repo.save(any(City.class)))
                .thenAnswer(saveAnswer());
    }

    public void mockCityRepoBean(CityRepo cityRepo) {
        mockSave(cityRepo);
        mockFindById(cityRepo);
        mockFindByZipcode(cityRepo);
        mockFindAll(cityRepo);
    }

    private Answer<Object> saveAnswer() {
        return invocationOnMock -> {
            City city = invocationOnMock.getArgument(0, City.class);

            if (Objects.isNull(city.getId()) || !cityStorageById.containsKey(city.getId())) {
                city.setId(currentId++);
            }

            cityStorageById.put(city.getId(), city);

            return city;
        };
    }

    private Answer<Object> findAllAnswer() {
        return invocationOnMock -> cityStorageById.values();
    }

    private Answer<Object> findByZipcodeAnswer() {
        return invocationOnMock -> {
            String zipcode = invocationOnMock.getArgument(0, String.class);
            List<City> cityList = cityStorageById.values().stream().filter(c -> c.getZipcode().equals(zipcode)).collect(Collectors.toList());
            if (cityList.isEmpty()) {
                return Optional.empty();
            } else if (cityList.size() != 1) {
                throw new IllegalStateException("More than one cities with equal zipcodes where found");
            }
            return Optional.ofNullable(cityList.get(0));
        };
    }

    private Answer<Object> findByIdAnswer() {
        return invocationOnMock -> {
            Long id = invocationOnMock.getArgument(0, Long.class);
            return Optional.ofNullable(cityStorageById.get(id));
        };
    }


}
