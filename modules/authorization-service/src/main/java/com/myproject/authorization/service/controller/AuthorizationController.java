package com.myproject.authorization.service.controller;

import com.myproject.authorization.api.dto.TokenDTO;
import com.myproject.authorization.api.service.AuthorizationService;
import com.myproject.authorization.service.service.AuthService;
import com.myproject.usermanagement.api.data.UserDTO;
import com.myproject.usermanagement.api.data.ValidateCredentialsDTO;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AuthorizationController implements AuthorizationService {

    private final AuthService authService;

    @Override
    public TokenDTO getToken(ValidateCredentialsDTO validateCredentialsDTO) {
        return authService.getToken(validateCredentialsDTO);
    }

    @Override
    public Map<String, Object> validate(@RequestHeader("X-Token") String token) {
        return authService.validateToken(token);
    }
}
