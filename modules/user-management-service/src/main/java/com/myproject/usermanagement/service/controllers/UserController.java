package com.myproject.usermanagement.service.controllers;

import com.myproject.usermanagement.api.data.UserDTO;
import com.myproject.usermanagement.api.data.ValidateCredentialsDTO;
import com.myproject.usermanagement.api.service.UserManagementService;
import com.myproject.usermanagement.service.dto.Converter;
import com.myproject.usermanagement.service.models.User;
import com.myproject.usermanagement.service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequiredArgsConstructor
public class UserController implements UserManagementService {

    private final UserService userService;

    public UserDTO validateCredentials(ValidateCredentialsDTO request){
        return userService.validateCredentials(request);
    }


    public UserDTO getUser(Long userId){
        return userService.getUser(userId);
    }

    @Override
    public Map<String, Object> adminViewApi() {
        return userService.adminViewApi();
    }

    @Override
    public Map<String, Object> adminEditApi() {
        return userService.adminEditApi();
    }

    @Override
    public Map<String, Object> financeViewApi() {
        return userService.financeViewApi();
    }

    @Override
    public Map<String, Object> financeEditApi() {
        return userService.financeEditApi();
    }
}
