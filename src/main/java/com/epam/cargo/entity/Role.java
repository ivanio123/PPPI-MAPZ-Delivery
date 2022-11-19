package com.epam.cargo.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, MANAGER;

    @Override
    public String getAuthority() {
        return name();
    }
}
