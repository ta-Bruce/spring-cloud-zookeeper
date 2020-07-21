package com.myproject.usermanagement.service.repositories;

import com.myproject.usermanagement.service.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
