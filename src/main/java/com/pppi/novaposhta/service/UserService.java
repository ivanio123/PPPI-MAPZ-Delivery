package com.pppi.novaposhta.service;

import com.pppi.novaposhta.dto.AddressRequest;
import com.pppi.novaposhta.dto.UserRequest;
import com.epam.cargo.entity.*;
import com.epam.cargo.exception.*;
import com.pppi.novaposhta.entity.*;
import com.pppi.novaposhta.repos.UserRepo;
import com.pppi.novaposhta.exception.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class UserService implements UserDetailsService {

    @Value("${validation.password.regexp}")
    private String passwordValidRegex;

    @Value("${validation.login.regexp}")
    private String loginValidRegex;

    @Value("${spring.messages.basename}")
    private String messages;

    @Value("${registration.bonus}")
    private BigDecimal bonus;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CityService cityService;

    @Autowired
    private DeliveryApplicationService applicationService;

    @Autowired
    private DeliveryReceiptService receiptService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User registerUser(UserRequest userRequest) throws WrongDataException {
        Locale locale = LocaleContextHolder.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle(messages, locale);
        User user = new User();
        initializePersonalData(userRequest, user);
        initializeCredentials(userRequest, user, bundle);

        this.addUser(user);
        return user;
    }

    private void initializeCredentials(UserRequest userRequest, User user, ResourceBundle bundle) throws WrongDataException {
        String login = userRequest.getLogin();
        requireValidLogin(login, bundle);
        requireUniqueLogin(login, bundle);
        user.setLogin(login);

        String password = userRequest.getPassword();
        requireValidPassword(password, bundle);
        requirePasswordDuplicationMatch(userRequest, password, bundle);
        user.setPassword(passwordEncoder.encode(password));
    }

    private void requirePasswordDuplicationMatch(UserRequest userRequest, String password, ResourceBundle bundle) throws PasswordConfirmationException {
        if (!password.equals(userRequest.getDuplicatePassword())){
            throw new PasswordConfirmationException(bundle);
        }
    }

    private void requireValidPassword(String password, ResourceBundle bundle) throws NoValidPasswordException {
        if (!isValidPassword(password)){
            throw new NoValidPasswordException(bundle, password);
        }
    }

    private boolean isValidPassword(String password) {
        return password.matches(passwordValidRegex);
    }

    private boolean isValidLogin(String login){
        return login.matches(loginValidRegex);
    }

    private void requireValidLogin(String login, ResourceBundle bundle) throws WrongDataException{
        if (!isValidLogin(login)){
            throw new NoValidLoginException(bundle, login);
        }
    }

    private void requireUniqueLogin(String login, ResourceBundle bundle) throws OccupiedLoginException {
        if (!Objects.isNull(userRepo.findByLogin(login))){
            throw new OccupiedLoginException(bundle, login);
        }
    }

    private void initializePersonalData(UserRequest userRequest, User user) throws WrongDataException{
        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        assignAddressToUser(userRequest, user);
    }

    private boolean assignAddressToUser(UserRequest userRequest, User user) throws NoExistingCityException {
        AddressRequest addressRequest = userRequest.getAddress();
        if (!Objects.isNull(addressRequest)) {

            City city = cityService.findCityById(addressRequest.getCityId());
            if (!Objects.isNull(city)){
                Address address = new Address();
                address.setCity(city);
                address.setStreet(addressRequest.getStreetName());
                address.setHouseNumber(addressRequest.getHouseNumber());

                addressService.addAddress(address);
                user.setAddress(address);
                return true;
            }
        }
        return false;
    }
    /**
     * Add user to database
     * @param user given User
     * @throws IllegalArgumentException if user has inappropriate fields or null mandatory ones
     * @return true if new User has been just added, or false if User is null or already exists
     * */
    public boolean addUser(User user) throws NoExistingCityException {
        if (Objects.isNull(user)){
            return false;
        }
        requireValidUser(user);

        User foundUser;
        if (!Objects.isNull(foundUser = userRepo.findByLogin(user.getLogin()))){
            user.setId(foundUser.getId());
            return false;
        }
        user.setRoles(Collections.singleton(Role.USER));

        Address address = user.getAddress();
        if (!Objects.isNull(address)) {
            addressService.addAddress(address);
        }
        user.setCash(bonus);
        userRepo.save(user);

        return true;
    }

    private void requireValidUser(User user) {
        Optional.ofNullable(user.getLogin()).orElseThrow(()->new IllegalArgumentException("User login cannot be null!"));
    }

    public User findUserById(Long id){
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepo.findByLogin(s);
    }


    public User findUserByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    public void deleteUser(User user){
        userRepo.delete(user);
    }

    public List<DeliveryApplication> getApplications(User user){
        if(!Hibernate.isInitialized(user.getApplications())){
            user.setApplications(applicationService.findAllByUserId(user.getId()));
        }
        return user.getApplications();
    }

    public List<DeliveryReceipt> getReceipt(User user){
        if(!Hibernate.isInitialized(user.getReceipts())){
            user.setReceipts(receiptService.findAllByCustomerId(user.getId()));
        }
        return user.getReceipts();
    }

    public Page<DeliveryApplication> getApplications(User user, Pageable pageable) {
        return applicationService.findAllByUserId(user.getId(), pageable);
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }

    public boolean credentialsEquals(User customer, User initiator) {
        return ServiceUtils.credentialsEquals(customer, initiator);
    }

    public Page<DeliveryReceipt> getCustomerReceipts(User customer, Pageable pageable) {
        return receiptService.findAllByCustomerId(customer.getId(), pageable);
    }
}
