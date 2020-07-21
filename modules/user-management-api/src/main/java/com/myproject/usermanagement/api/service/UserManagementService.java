package com.myproject.usermanagement.api.service;

import com.myproject.usermanagement.api.data.UserDTO;
import com.myproject.usermanagement.api.data.ValidateCredentialsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

@RequestMapping("/users")
public interface UserManagementService {

    @PostMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    UserDTO validateCredentials(@Valid @RequestBody ValidateCredentialsDTO request);

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserDTO getUser(@PathVariable("id")  @NotNull(message = "{user.id.invalid}") Long userId);

    @GetMapping("/admin/view")
    @ResponseStatus(HttpStatus.OK)
    Map<String, Object> adminViewApi();

    @GetMapping("/admin/edit")
    @ResponseStatus(HttpStatus.OK)
    Map<String, Object> adminEditApi();

    @GetMapping("/finance/view")
    @ResponseStatus(HttpStatus.OK)
    Map<String, Object> financeViewApi();

    @GetMapping("/finance/edit")
    @ResponseStatus(HttpStatus.OK)
    Map<String, Object> financeEditApi();

}
