package com.pppi.novaposhta.service;

import com.pppi.novaposhta.dto.UserRequest;
import com.pppi.novaposhta.entity.Address;
import com.pppi.novaposhta.entity.User;
import com.pppi.novaposhta.exception.NoExistingCityException;
import com.pppi.novaposhta.exception.NoValidPasswordException;
import com.pppi.novaposhta.repos.UserRepo;
import com.pppi.novaposhta.mock.UserMockEnvironment;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;


@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Value("${spring.messages.basename}")
    private String messages;

    @Value("${registration.bonus}")
    private BigDecimal bonus;

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private AddressService addressService;

    private UserMockEnvironment mockEnvironment;

    public static Stream<Arguments> userRegistrationRequestsCases() {
        return Stream.of(
                Arguments.of(UserRequest.builder().name("Ivan").surname("Sipalka").login("PIPAS").password("qwerty12345").duplicatePassword("qwerty12345").build())
        );
    }

    public static Stream<Arguments> userRegistrationFailTestCases() {
        return Stream.of(
                Arguments.of(UserRequest.builder().build()),
                Arguments.of(UserRequest.builder().login("Ted").password("log4j2345").duplicatePassword("log4j2345").build()),
                Arguments.of(UserRequest.builder().login("Ted").password("log4j2345").duplicatePassword("4874kgty0").build()),
                Arguments.of(UserRequest.builder().login("Ted").password("log4j2345").duplicatePassword("log4j2345").name("Teodor").build()),
                Arguments.of(UserRequest.builder().login("Ted").password("log4j2345").duplicatePassword("log4j2345").surname("Roosevelt").build()),
                Arguments.of(UserRequest.builder().login("Ted").password("log4j2345").duplicatePassword("log4j2345").name("Teodor").surname("Roosevelt").build()),
                Arguments.of(UserRequest.builder().login("oLeg").password("fjdhf8344").duplicatePassword("fjdhf8344").surname("Klymchuk").phone("+380985768932").build()),
                Arguments.of(UserRequest.builder().login("oLeg").password("fjdhf8344").duplicatePassword("ff8343rgi").name("Oleg").surname("Klymchur").phone("+380985768932").build()),
                Arguments.of(UserRequest.builder().login("Catalina").build()),
                Arguments.of(UserRequest.builder().name("Merry").surname("Jane").build()),
                Arguments.of(UserRequest.builder().name("Merry").surname("Jane").build()),
                Arguments.of(UserRequest.builder().name("Merry").surname("Jane").phone("+380986378087").email("somemail@mail.com").build()),
                Arguments.of(UserRequest.builder().login("Lalafanfan").password("pass").duplicatePassword("pass").name("Merry").surname("Jane").phone("+380986378087").email("somemail@mail.com").build()),
                Arguments.of(UserRequest.builder().login("Lalafanfan").password("frhun349434").duplicatePassword("frhun349434").name("merry").surname("Jane").phone("+380986378087").email("somemail@mail.com").build()),
                Arguments.of(UserRequest.builder().login("Lalafanfan").password("frhun349434").duplicatePassword("frhun349434").name("Merry").surname("jane").phone("+380986378087").email("somemail@mail.com").build()),
                Arguments.of(UserRequest.builder().login("Lalafanfan").password("frhun349434").duplicatePassword("frhun349434").name("Merry32").surname("Ja23ne").phone("+380986378087").email("somemail@mail.com").build())
        );
    }

    @BeforeEach
    public void setUp(){
        try {
            Mockito.doReturn(false).when(addressService).addAddress(any());
        } catch (NoExistingCityException e) {
            e.printStackTrace();
        }
        mockEnvironment = new UserMockEnvironment();
        mockEnvironment.mockUserRepoBean(userRepo);
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("userRegistrationRequestsCases")
    public void registerUser(UserRequest registerUserRequest){

        User registered = userService.registerUser(registerUserRequest);

        Assertions.assertNotNull(registered.getId());
        Assertions.assertEquals(bonus, registered.getCash());
        Assertions.assertEquals(registered, userService.findUserById(registered.getId()));
    }

    @Test
    @SneakyThrows
    void addUser() {

        User user = new User();
        String name = "Maksym";
        String login = "maksymko";
        String password = "sp3ng48v31";

        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);

        userService.addUser(user);

        User addedUser = userService.findUserByLogin(login);

        Assertions.assertEquals(addedUser.getCash(), bonus);
        Assertions.assertEquals(user.getId(), addedUser.getId());
    }

    @ParameterizedTest
    @MethodSource("userRegistrationFailTestCases")
    public  void userRegistrationFailTest(UserRequest registerRequest){
        Assertions.assertThrows(Exception.class, ()->userService.registerUser(registerRequest));
    }


    @Test
    @SneakyThrows
    void addUserAndDeleteThen(){

        User user = new User();
        String name = "User";
        String login = "admin";
        String password = "Qwerty12345";

        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);

        userService.addUser(user);

        Assertions.assertEquals(password, userService.loadUserByUsername(login).getPassword());
        Assertions.assertEquals(user.getId(), userService.findUserByLogin(login).getId());

        userService.deleteUser(user);

        Assertions.assertTrue(Objects.isNull(userService.findUserByLogin(login)));

    }

    @Test
    void addUserFailed(){
        User user = new User();
        Assert.assertThrows(IllegalArgumentException.class, ()->userService.addUser(user));
    }

    @Test
    @SneakyThrows
    void addUserWithAddress(){
        String zipcodeOfVinnitsia = "21012";
        Address address = new Address(cityService.findCityByZipCode(zipcodeOfVinnitsia), "Soborna", "68");

        String name = "Ivan";
        String login = "divan0_0";
        String password = "123";

        User user = User.builder().name(name).login(login).password(password).build();
        user.setAddress(address);

        userService.addUser(user);

        Assertions.assertEquals(user.getId(), userService.findUserByLogin(login).getId());
        Assertions.assertFalse(Objects.isNull(userService.findUserByLogin(login).getAddress()));
    }


    public static Stream<Arguments> testPasswordCases() {
        return Stream.of(
                Arguments.of("azaazazaaz", false),
                Arguments.of("ElFierro31415", true),
                Arguments.of("Hjnd_weri23", true),
                Arguments.of("1_small", false),
                Arguments.of("GOlden Dragons1", false),
                Arguments.of("_-_-_-_\n-_-_-_-_", false),
                Arguments.of("Forbidden~symbol", false),
                Arguments.of("FordGT40", true)
        );
    }

    private static Stream<Arguments> testWrongPasswordCases() {
        return Stream.of(
                Arguments.of("azaazazaaz"),
                Arguments.of("1_small"),
                Arguments.of("GOlden Dragons1"),
                Arguments.of("_-_-_-_\n-_-_-_-_"),
                Arguments.of("Forbidden~symbol")
        );
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("testPasswordCases")
    void isValidPasswordTest(String password, Boolean valid) {
        Method isValidPasswordMethod = userService.getClass().getDeclaredMethod("isValidPassword", String.class);
        isValidPasswordMethod.setAccessible(true);
        Assertions.assertEquals(valid, isValidPasswordMethod.invoke(userService, password));
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("testWrongPasswordCases")
    void requireValidPasswordWrongDataTest(String password) {
        Method requireValidPasswordMethod = userService.getClass().getDeclaredMethod("requireValidPassword", String.class, ResourceBundle.class);
        requireValidPasswordMethod.setAccessible(true);
        try{
            requireValidPasswordMethod.invoke(userService, password, ResourceBundle.getBundle(messages));
            Assertions.fail();
        }
        catch (InvocationTargetException e){
            if (!(e.getTargetException() instanceof NoValidPasswordException)){
                Assertions.fail();
            }
        }
    }
}