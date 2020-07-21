package com.myproject.common.security.api.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthToken extends UsernamePasswordAuthenticationToken {

    public JwtAuthToken(Object token) {
        super(null, token);
    }

    public JwtAuthToken(Object principal, Object token, Collection<? extends GrantedAuthority> authorities) {
        super(principal, token, authorities);
    }

}
