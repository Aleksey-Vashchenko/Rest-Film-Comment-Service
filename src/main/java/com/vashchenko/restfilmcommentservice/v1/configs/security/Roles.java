package com.vashchenko.restfilmcommentservice.v1.configs.security;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    ROLE_USER,ROLE_ADMIN, ROLE_MANAGER;


    @Override
    public String getAuthority() {
        return this.name();
    }
}
