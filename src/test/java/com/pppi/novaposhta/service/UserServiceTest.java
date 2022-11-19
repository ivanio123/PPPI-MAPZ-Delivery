package com.pppi.novaposhta.service;

import com.pppi.novaposhta.entity.Address;
import com.pppi.novaposhta.entity.User;
import com.pppi.novaposhta.exception.NoValidPasswordException;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Stream;


@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Value("${spring.messages.basename}")
    private String messages;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CityService cityService;

    @Test
    @SneakyThrows
    void addUser() {
        User user = new User();
        String name = "Roman";
        String login = "romanko_03";
        String password = "1234";

        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);

        userService.addUser(user);

        Assert.assertEquals(password, userService.loadUserByUsername(login).getPassword());
        Assert.assertEquals(user.getId(), userService.findUserByLogin(login).getId());
    }

    @Test
    @SneakyThrows
    void addUserAndDeleteThen(){
        User user = new User();
        String name = "User";
        String login = "admin";
        String password = "12345";

        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);

        userService.addUser(user);

        Assert.assertEquals(password, userService.loadUserByUsername(login).getPassword());
        Assert.assertEquals(user.getId(), userService.findUserByLogin(login).getId());

        userService.deleteUser(user);

        Assert.assertTrue(Objects.isNull(userService.findUserByLogin(login)));

    }

    @Test
    void addUserFailed(){
        User user = new User();
        Assert.assertThrows(DataIntegrityViolationException.class, ()->userService.addUser(user));
    }

    @Test
    @SneakyThrows
    void addUserWithAddress(){
        String zipcodeOfVinnitsia = "21012";
        Address address = new Address(cityService.findCityByZipCode(zipcodeOfVinnitsia), "Soborna", "68");

        String name = "Ivan";
        String login = "divan0_0";
        String password = "123";

        User user = new User(name, login, password);
        user.setAddress(address);

        userService.addUser(user);

        Assert.assertEquals(password, userService.loadUserByUsername(login).getPassword());
        Assert.assertEquals(user.getId(), userService.findUserByLogin(login).getId());

        Assert.assertFalse(Objects.isNull(userService.findUserByLogin(login).getAddress()));
    }

    @Test
    @SneakyThrows
    void addUserWithAddressAndDeleteThan(){
        String zipcodeOfVinnitsia = "21012";
        Address address = new Address(cityService.findCityByZipCode(zipcodeOfVinnitsia), "Soborna", "96");

        String name = "Ivan";
        String login = "divan1_1";
        String password = "123";

        User user = new User(name, login, password);
        user.setAddress(address);

        userService.addUser(user);

        Assert.assertEquals(password, userService.loadUserByUsername(login).getPassword());
        Assert.assertEquals(user.getId(), userService.findUserByLogin(login).getId());

        Assert.assertFalse(Objects.isNull(userService.findUserByLogin(login).getAddress()));

        userService.deleteUser(user);

        Assert.assertTrue(Objects.isNull(userService.findUserByLogin(login)));

        Assert.assertThrows(NoSuchElementException.class, ()->addressService.findAddressById(address.getId()));
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
        Assert.assertEquals(valid, isValidPasswordMethod.invoke(userService, password));
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("testWrongPasswordCases")
    void requireValidPasswordWrongDataTest(String password) {
        Method requireValidPasswordMethod = userService.getClass().getDeclaredMethod("requireValidPassword", String.class, ResourceBundle.class);
        requireValidPasswordMethod.setAccessible(true);
        try{
            requireValidPasswordMethod.invoke(userService, password, ResourceBundle.getBundle(messages));
            Assert.fail();
        }
        catch (InvocationTargetException e){
            if (!(e.getTargetException() instanceof NoValidPasswordException)){
                Assert.fail();
            }
        }
    }
}