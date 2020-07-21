package com.myproject.authorization.api.dto;

import com.myproject.usermanagement.api.data.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {

    private String token;
    private UserDTO user;
}
