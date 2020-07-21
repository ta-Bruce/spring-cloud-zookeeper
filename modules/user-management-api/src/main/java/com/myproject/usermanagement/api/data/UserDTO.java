package com.myproject.usermanagement.api.data;

import lombok.Data;

import java.util.Collection;

@Data
public class UserDTO {

    private Long id;
    private String username;
    private Collection<RoleDTO> roles;
}
