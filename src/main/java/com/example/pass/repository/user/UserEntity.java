package com.example.pass.repository.user;

import com.example.pass.repository.BaseEntity;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Getter
@ToString
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {
    @Id
    private String userId;
    private String userName;
    private String status;
    private String phone;
    private String meta;
}
