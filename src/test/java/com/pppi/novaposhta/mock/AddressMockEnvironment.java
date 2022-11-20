package com.pppi.novaposhta.mock;

import com.pppi.novaposhta.entity.Address;
import com.pppi.novaposhta.entity.City;
import com.pppi.novaposhta.repos.AddressRepo;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;

public class AddressMockEnvironment {

    private Long currentId = 1L;

    private Map<Long, Address> addressStorageById = new HashMap<>();

    public void mockFindById(AddressRepo repo){
        Mockito.when(repo.findById(any(Long.class)))
                .thenAnswer(findByIdAnswer());
    }

    public void mockFindAll(AddressRepo repo){
        Mockito.when(repo.findAll())
                .thenAnswer(findAllAnswer());
    }

    public void mockFindByHouseNumberAndCityAndStreet(AddressRepo repo){
        Mockito.when(repo.findByHouseNumberAndCityAndStreet(any(String.class), any(City.class), any(String.class)))
                .thenAnswer(findByHouseNumberAndCityAndStreetAnswer());
    }

    public void mockSave(AddressRepo repo){
        Mockito.when(repo.save(Mockito.any(Address.class)))
                .thenAnswer(saveAnswer());
    }

    public void mockDelete(AddressRepo repo){
        Mockito.doAnswer(deleteAnswer()).when(repo).delete(any(Address.class));
    }

    public void mockAddressRepoBean(AddressRepo addressRepo) {
        mockSave(addressRepo);
        mockFindById(addressRepo);
        mockFindAll(addressRepo);
        mockFindByHouseNumberAndCityAndStreet(addressRepo);
        mockDelete(addressRepo);
    }

    private Answer<Object> deleteAnswer() {
        return invocationOnMock -> {
            Address address = invocationOnMock.getArgument(0, Address.class);
            Optional.of(address).ifPresent(a -> addressStorageById.remove(a.getId()));
            return address;
        };
    }

    private Answer<Object> saveAnswer() {
        return invocationOnMock -> {
            Address address = invocationOnMock.getArgument(0, Address.class);
            if (Objects.isNull(address.getId()) || !addressStorageById.containsKey(address.getId())){
                address.setId(currentId++);
            }
            addressStorageById.put(address.getId(), address);
            return address;
        };
    }

    private Answer<Object> findByHouseNumberAndCityAndStreetAnswer() {
        return invocationOnMock -> {
            String houseNumber = invocationOnMock.getArgument(0, String.class);
            City city = invocationOnMock.getArgument(1, City.class);
            String street = invocationOnMock.getArgument(2, String.class);

            List<Address> found = addressStorageById.values()
                    .stream().filter(
                            equalsCityPredicate(city)
                                    .and(equalsStreetPredicate(street))
                                    .and(equalsHouseNumberPredicate(houseNumber)
                                    )
                    ).collect(Collectors.toList());

            if (found.isEmpty()) {
                return null;
            } else if (found.size() != 1) {
                throw new IllegalStateException("More than one address was found!");
            }
            return found.get(0);
        };
    }

    private Predicate<Address> equalsCityPredicate(City city) {
        return a -> a.getCity().equals(city);
    }

    private Predicate<Address> equalsStreetPredicate(String street) {
        return a -> a.getStreet().equals(street);
    }

    private Predicate<Address> equalsHouseNumberPredicate(String houseNumber) {
        return a -> a.getHouseNumber().equals(houseNumber);
    }

    private Answer<Object> findAllAnswer() {
        return invocationOnMock -> addressStorageById.values();
    }

    private Answer<Object> findByIdAnswer() {
        return invocationOnMock -> {
            Long id = invocationOnMock.getArgument(0, Long.class);
            return Optional.ofNullable(addressStorageById.get(id));
        };
    }
}
