package com.example.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRespDTO implements Serializable {

    private int id;
    private String name;
    private int age;
    private String email;
    private String username;
    private String password;
    private String[] rolesNames;
}
