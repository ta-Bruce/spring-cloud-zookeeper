package com.myproject.usermanagement.service.repositories;

import com.myproject.usermanagement.service.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
