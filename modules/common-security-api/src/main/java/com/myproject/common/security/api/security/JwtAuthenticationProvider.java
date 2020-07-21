package com.myproject.common.security.api.security;

import com.myproject.authorization.api.service.AuthorizationService;
import com.myproject.common.exception.api.exception.HttpExceptions;
import com.myproject.common.security.api.exception.JwtAuthenticationException;
import com.myproject.usermanagement.api.data.PermissionDTO;
import com.myproject.usermanagement.api.data.RoleDTO;
import com.myproject.usermanagement.api.data.UserDTO;
import com.myproject.usermanagement.api.service.UserManagementService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

        private final AuthorizationService authorizationService;
        private final UserManagementService userManagementService;

        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {

            String token = authentication.getCredentials().toString();
            if (token.isEmpty())
                throw new JwtAuthenticationException(new HttpExceptions.UnauthorizedException("authorization.token.empty"));
            Map<String, Object> claims;
            UserDTO user;
            try {
                claims = authorizationService.validate(token);
                user = userManagementService.getUser(Long.parseLong(String.valueOf(claims.get("id"))));
            } catch (FeignException e){
                throw new JwtAuthenticationException(e);
            } catch (HttpExceptions.AbstractHttpException e){
                throw new JwtAuthenticationException(e);
            }
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            for (RoleDTO role : user.getRoles()){
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
                for(PermissionDTO permission : role.getPermissions())
                    authorities.add(new SimpleGrantedAuthority(permission.getName()));
            }
            UserDetails userDetails = User.builder()
                                        .username(user.getUsername())
                                        .password(token)
                                        .authorities(authorities)
                                        .build();
            return new JwtAuthToken(userDetails, token, userDetails.getAuthorities());
        }

        @Override
        public boolean supports(Class<?> authentication) {
            return JwtAuthToken.class.isAssignableFrom(authentication);
        }
}
