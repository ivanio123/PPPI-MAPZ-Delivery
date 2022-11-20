package com.pppi.novaposhta.mock;

import com.pppi.novaposhta.entity.DeliveredBaggage;
import com.pppi.novaposhta.repos.DeliveredBaggageRepo;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class DeliveredBaggageMockEnvironment {

    private Long currentId = 1L;

    private Map<Long, DeliveredBaggage> baggageStorageById = new HashMap<>();

    public void mockDeliveredBaggageRepoBean(DeliveredBaggageRepo repo) {
        mockSave(repo);
        findById(repo);
    }

    private void findById(DeliveredBaggageRepo repo) {
        Mockito.when(repo.findById(Mockito.any()))
                .thenAnswer(findByIdMock());
    }

    private void mockSave(DeliveredBaggageRepo repo) {
        Mockito.when(repo.save(Mockito.any()))
                .thenAnswer(saveMock());
    }

    private Answer<Optional<DeliveredBaggage>> findByIdMock() {
        return invocationOnMock -> {
            Long id = invocationOnMock.getArgument(0, Long.class);
            return Optional.ofNullable(baggageStorageById.get(id));
        };
    }

    private Answer<DeliveredBaggage> saveMock() {
        return invocationOnMock -> {
            DeliveredBaggage baggage = invocationOnMock.getArgument(0, DeliveredBaggage.class);
            if (Objects.isNull(baggage.getId()) || !baggageStorageById.containsKey(baggage.getId())){
                baggage.setId(currentId++);
            }
            baggageStorageById.put(baggage.getId(), baggage);
            return baggage;
        };
    }
}
