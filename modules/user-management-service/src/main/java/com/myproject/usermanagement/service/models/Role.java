package com.myproject.usermanagement.service.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "roles")
@Getter @Setter
public class Role extends BaseModel {

    @Column(name = "name", nullable = false, length = 32)
    private String name;
    @Column(name = "description", nullable = true, length = 255)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_permissions",
            joinColumns = @JoinColumn(name="role_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"role_id", "permission_id"}))
    private Collection<Permission> rolePermissions;
}
