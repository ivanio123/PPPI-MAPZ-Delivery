package com.pppi.novaposhta.mock;

import com.pppi.novaposhta.entity.DeliveryApplication;
import com.pppi.novaposhta.repos.DeliveryApplicationRepo;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;

/**
 * Designed specially for mocking delivery application's db repositories
 * Assign generated id for saving object if id is missing, or not exist in locale storage
 * @author Roman Kovalchuk
 * @since 20.02.2022
 * */
public class DeliveryApplicationMockEnvironment {

    private Long currentId = 1L;

    private Map<Long, DeliveryApplication> applicationsStorageById = new HashMap<>();

    public void mockFindById(DeliveryApplicationRepo repo){
        Mockito.when(repo.findById(any(Long.class)))
                .thenAnswer(findByIdAnswer());
    }

    public void mockFindAllByUserId(DeliveryApplicationRepo repo){
        Mockito.when(repo.findAllByUserId(any(Long.class)))
                .thenAnswer(findAllByUserIdAnswer());
    }

    public void mockFindAll(DeliveryApplicationRepo repo){
        Mockito.when(repo.findAll())
                .thenAnswer(findAllAnswer());
    }

    public void mockSave(DeliveryApplicationRepo repo){
        Mockito.when(repo.save(any(DeliveryApplication.class)))
                .thenAnswer(saveAnswer());
    }

    public void mockDeliveryApplicationRepoBean(DeliveryApplicationRepo repo){
        mockSave(repo);
        mockFindById(repo);
        mockFindAll(repo);
        mockFindAllByUserId(repo);
    }

    private Answer<Object> saveAnswer() {
        return invocationOnMock -> {
            DeliveryApplication application = invocationOnMock.getArgument(0, DeliveryApplication.class);
            if (!Objects.isNull(application.getId()) && applicationsStorageById.containsKey(application.getId())) {
                applicationsStorageById.put(application.getId(), application);
            } else {
                application.setId(currentId++);
                applicationsStorageById.put(application.getId(), application);
            }
            return application;
        };
    }

    private Answer<Object> findByIdAnswer() {
        return invocationOnMock -> {
            Long id = invocationOnMock.getArgument(0, Long.class);
            return Optional.ofNullable(applicationsStorageById.get(id));
        };
    }

    private Answer<Object> findAllAnswer() {
        return invocationOnMock -> applicationsStorageById.values();
    }

    private Answer<Object> findAllByUserIdAnswer() {
        return invocationOnMock -> {
            Long userId = invocationOnMock.getArgument(0, Long.class);
            return applicationsStorageById.values().stream().filter(applicationCustomerIdPredicate(userId)).collect(Collectors.toList());
        };
    }

    private Predicate<DeliveryApplication> applicationCustomerIdPredicate(Long userId) {
        return a -> a.getCustomer().getId().equals(userId);
    }
}
