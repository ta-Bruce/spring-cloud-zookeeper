package com.myproject.usermanagement.service.repositories;

import com.myproject.usermanagement.service.models.Permission;
import org.springframework.data.repository.CrudRepository;

public interface PermissionRepository extends CrudRepository<Permission, Long> {
}
