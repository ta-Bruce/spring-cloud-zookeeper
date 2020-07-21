package com.myproject.usermanagement.service.dto;


import com.myproject.usermanagement.api.data.PermissionDTO;
import com.myproject.usermanagement.api.data.RoleDTO;
import com.myproject.usermanagement.api.data.UserDTO;
import com.myproject.usermanagement.service.models.Permission;
import com.myproject.usermanagement.service.models.Role;
import com.myproject.usermanagement.service.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface Converter {

    @Mappings({
            @Mapping(target="roles", source="source.userRoles")
    })
    UserDTO toDTO(User source);

    @Mappings({
            @Mapping(target="permissions", source="source.rolePermissions")
    })
    RoleDTO toDTO(Role source);

    PermissionDTO toDTO(Permission source);

}