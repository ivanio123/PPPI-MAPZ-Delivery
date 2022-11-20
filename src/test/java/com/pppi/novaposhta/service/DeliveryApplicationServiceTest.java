package com.pppi.novaposhta.service;

import com.epam.cargo.entity.*;
import com.pppi.novaposhta.entity.*;
import com.pppi.novaposhta.exception.NoExistingCityException;
import com.epam.cargo.mock.*;
import com.epam.cargo.repos.*;
import com.pppi.novaposhta.mock.*;
import com.pppi.novaposhta.repos.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
class DeliveryApplicationServiceTest {

    @Autowired
    private DeliveryApplicationService applicationService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeliveredBaggageService deliveredBaggageService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AddressService addressService;

    @MockBean
    private DeliveryApplicationRepo deliveryApplicationRepo;

    @MockBean
    private DeliveredBaggageRepo deliveredBaggageRepo;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private CityRepo cityRepo;

    @MockBean
    private AddressRepo addressRepo;

    @BeforeEach
    public void setUp(){
        DeliveryApplicationMockEnvironment applicationMockEnvironment = new DeliveryApplicationMockEnvironment();
        applicationMockEnvironment.mockDeliveryApplicationRepoBean(deliveryApplicationRepo);

        UserMockEnvironment userMockEnvironment = new UserMockEnvironment();
        userMockEnvironment.mockUserRepoBean(userRepo);

        DeliveredBaggageMockEnvironment baggageMockEnvironment = new DeliveredBaggageMockEnvironment();
        baggageMockEnvironment.mockDeliveredBaggageRepoBean(deliveredBaggageRepo);

        CityMockEnvironment cityMockEnvironment = new CityMockEnvironment();
        cityMockEnvironment.mockCityRepoBean(cityRepo);

        AddressMockEnvironment addressMockEnvironment = new AddressMockEnvironment();
        addressMockEnvironment.mockAddressRepoBean(addressRepo);

    }

    public static Stream<Arguments> applicationsToSaveTestCases() {
        return Stream.of(
                Arguments.of(DeliveryApplication.builder()
                        .customer(testCustomer())
                        .deliveredBaggage(testBaggage())
                        .senderAddress(Address.builder().city(new City("Vinnytsia", CityZipcodesConstants.VINNYTSIA_ZIPCODE)).street("Podilya").houseNumber("1").build())
                        .receiverAddress(Address.builder().city(new City("Kyiv", CityZipcodesConstants.KYIV_ZIPCODE)).street("Chreschatyk").houseNumber("1").build())
                        .price(100d)
                        .build()
                )
        );
    }


    public static Stream<Arguments> applicationsToSaveFailTestCases() {
        return Stream.of(
                Arguments.of(
                        DeliveryApplication.builder()
                                .customer(null)
                                .deliveredBaggage(testBaggage())
                                .senderAddress(testSenderAddress())
                                .receiverAddress(testReceiverAddress())
                                .price(100d).build(),
                        prepareSenderAddress().andThen(prepareReceiverAddress()),
                        IllegalArgumentException.class
                ),
                Arguments.of(
                        testCorrectDeliveryApplication(),
                        prepareSenderAddress().andThen(prepareReceiverAddress()),
                        IllegalArgumentException.class
                ),
                Arguments.of(
                        DeliveryApplication.builder()
                                .customer(testCustomer())
                                .deliveredBaggage(null)
                                .senderAddress(testSenderAddress())
                                .receiverAddress(testReceiverAddress())
                                .price(100d).build(),
                        prepareSenderAddress().andThen(prepareReceiverAddress()).andThen(prepareUser()),
                        IllegalArgumentException.class
                ),
                Arguments.of(
                        testCorrectDeliveryApplication(),
                        prepareUser().andThen(prepareSenderAddress()),
                        NoExistingCityException.class
                ),
                Arguments.of(
                        testCorrectDeliveryApplication(),
                        prepareUser().andThen(prepareReceiverAddress()),
                        NoExistingCityException.class
                )
        );
    }

    private static DeliveryApplication testCorrectDeliveryApplication() {
        return DeliveryApplication.builder()
                .customer(testCustomer())
                .deliveredBaggage(testBaggage())
                .senderAddress(testSenderAddress())
                .receiverAddress(testReceiverAddress())
                .price(100d).build();
    }


    @SneakyThrows
    @ParameterizedTest
    @MethodSource("applicationsToSaveTestCases")
    void saveApplication(DeliveryApplication application) {
        prepareMockEnvironment(application);

        applicationService.saveApplication(application);
        Assertions.assertEquals(application, applicationService.findById(application.getId()));

    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("applicationsToSaveFailTestCases")
    void saveApplicationFail(DeliveryApplication application, BiConsumer<DeliveryApplication, DeliveryApplicationServiceTest> prepare, Class<? extends Exception> clazz) {
        prepare.accept(application, this);
        Assertions.assertThrows(clazz, ()->applicationService.saveApplication(application));
    }

    private void prepareMockEnvironment(DeliveryApplication application) throws NoExistingCityException {
        userRepo.save(application.getCustomer());
        cityService.addCity(application.getSenderAddress().getCity());
        cityService.addCity(application.getReceiverAddress().getCity());
    }

    private static Address testReceiverAddress() {
        return Address.builder().city(new City("Kyiv", CityZipcodesConstants.KYIV_ZIPCODE)).street("Chreschatyk").houseNumber("1").build();
    }

    private static Address testSenderAddress() {
        return Address.builder().city(new City("Vinnytsia", CityZipcodesConstants.VINNYTSIA_ZIPCODE)).street("Podilya").houseNumber("1").build();
    }

    private static BiConsumer<DeliveryApplication, DeliveryApplicationServiceTest> prepareSenderAddress() {
        return prepareAddress(DeliveryApplication::getSenderAddress);
    }

    private static BiConsumer<DeliveryApplication, DeliveryApplicationServiceTest> prepareReceiverAddress() {
        return prepareAddress(DeliveryApplication::getReceiverAddress);
    }

    private static BiConsumer<DeliveryApplication, DeliveryApplicationServiceTest> prepareAddress(Function<DeliveryApplication, Address> getAddressFunction){
        return new BiConsumer<>() {
            @SneakyThrows
            @Override
            public void accept(DeliveryApplication a, DeliveryApplicationServiceTest t) {
                Address address = getAddressFunction.apply(a);
                t.cityService.addCity(address.getCity());
                t.addressService.addAddress(address);
            }
        };
    }

    private static BiConsumer<DeliveryApplication, DeliveryApplicationServiceTest> prepareUser(){
        return (a, t)->{
            try {
                t.userService.addUser(a.getCustomer());
            } catch (NoExistingCityException e) {
                e.printStackTrace();
            }
        };
    }

    private static DeliveredBaggage testBaggage() {
        return DeliveredBaggage.builder().volume(1000d).weight(2d).type(BaggageType.STANDART).description("Test case baggage").build();
    }

    private static User testCustomer() {
        return User.builder().name("Ivan").surname("Sipalka").login("Pips").password("Password123").phone("+380976543826").build();
    }

}