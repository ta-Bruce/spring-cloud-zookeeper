package com.myproject.usermanagement.service.services;

import com.myproject.common.exception.api.exception.HttpExceptions;
import com.myproject.usermanagement.api.data.UserDTO;
import com.myproject.usermanagement.api.data.ValidateCredentialsDTO;
import com.myproject.usermanagement.service.dto.Converter;
import com.myproject.usermanagement.service.models.User;
import com.myproject.usermanagement.service.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Converter converter;

    public UserDTO validateCredentials(ValidateCredentialsDTO request){
        Optional<User> user = getUserRepository().findByUsername(request.getUsername());
        if (!user.isPresent())
            throw new HttpExceptions.UnauthorizedException("user.invalid.credentials.username", request.getUsername());
        if (!getPasswordEncoder().matches(request.getPassword(), user.get().getPassword()))
            throw new HttpExceptions.UnauthorizedException("user.invalid.credentials.password");
        return converter.toDTO(user.get());
    }

    public UserDTO getUser(Long id){
        Optional<User> user = getUserRepository().findById(id);
        if (!user.isPresent())
            throw new HttpExceptions.ResourceNotFoundException("user.id.not.found", id);
        return converter.toDTO(user.get());

    }


    @PreAuthorize("hasRole('Admin') and hasAuthority('View')")
    public Map<String, Object> adminViewApi() {
        return Collections.singletonMap("Admin", "View");
    }

    @PreAuthorize("hasRole('Admin') and hasAuthority('Edit')")
    public Map<String, Object> adminEditApi() {
        return Collections.singletonMap("Admin", "Edit");
    }

    @PreAuthorize("hasRole('Finance') and hasAuthority('View')")
    public Map<String, Object> financeViewApi() {
        return Collections.singletonMap("Finance", "View");
    }

    @PreAuthorize("hasRole('Finance') and hasAuthority('Edit')")
    public Map<String, Object> financeEditApi() {
        return Collections.singletonMap("Finance", "Edit");
    }


}
