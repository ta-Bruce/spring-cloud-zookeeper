package com.myproject.usermanagement.service.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Getter @Setter
public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;

    @CreationTimestamp
    @Column(name = "created_at")
     private Date createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    private Date modifiedAt;

}
