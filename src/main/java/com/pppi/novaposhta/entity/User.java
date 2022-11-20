package com.pppi.novaposhta.entity;

import com.pppi.novaposhta.exception.WrongInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Represents an end user.
 * @author group2
 * @version 1.0
 * */
@Entity
@Table(name="users")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", length = 32)
    private String name;

    @Column(name="surname", length = 32)
    private String surname;


    @NotBlank(message = WrongInput.NO_FILLED_LOGIN)
    @Column(name="login", unique = true, nullable = false, length = 32)
    private String login;

    @NotBlank(message = WrongInput.NO_FILLED_PASSWORD)
    @Column(name="password", nullable = false, length = 256)
    private String password;

    @Column(name="phone", length = 13)
    private String phone;

    @Column(name="email")
    private String email;

    @Column(name="cash")
    private BigDecimal cash;

    @OneToOne
    @JoinColumn(name="addressId")
    private Address address;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name="user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(mappedBy="customer", fetch=FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<DeliveryApplication> applications;


    @OneToMany(mappedBy="customer", fetch=FetchType.LAZY)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<DeliveryReceipt> receipts;

    public User() { }

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isManager(){
        return getRoles().contains(Role.MANAGER);
    }
}
