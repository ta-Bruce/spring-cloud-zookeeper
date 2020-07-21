package com.myproject.authorization.api.service;

import com.myproject.authorization.api.dto.TokenDTO;
import com.myproject.usermanagement.api.data.UserDTO;
import com.myproject.usermanagement.api.data.ValidateCredentialsDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RequestMapping("/auth")
public interface AuthorizationService {

    @PostMapping("get-token")
    TokenDTO getToken(@Valid @RequestBody ValidateCredentialsDTO validateCredentialsDTO);

    @GetMapping("validate")
    Map<String, Object> validate(@RequestHeader("X-Token") String token);


}
