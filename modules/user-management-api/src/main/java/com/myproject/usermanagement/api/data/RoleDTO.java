package com.myproject.usermanagement.api.data;

import lombok.Data;

import java.util.Collection;

@Data
public class RoleDTO {

    private String name;
    private Collection<PermissionDTO> permissions;
}
