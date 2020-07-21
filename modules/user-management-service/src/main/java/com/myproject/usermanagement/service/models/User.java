package com.myproject.usermanagement.service.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
@Getter(AccessLevel.PUBLIC) @Setter
public class User extends BaseModel {

    @Column(name = "username", nullable = false, length = 32)
    private String username;
    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}))
    private Collection<Role> userRoles;
}
