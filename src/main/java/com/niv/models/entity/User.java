package com.niv.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "users")
@Data
public class User extends BaseModel{
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String mobileNo;
    private String password;
}
