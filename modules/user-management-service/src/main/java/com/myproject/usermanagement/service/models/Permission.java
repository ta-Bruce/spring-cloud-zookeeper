package com.myproject.usermanagement.service.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "permissions")
@Getter @Setter
public class Permission extends BaseModel {

    @Column(name = "name", nullable = false, length = 32)
    private String name;
    @Column(name = "description", nullable = true, length = 255)
    private String description;
}
