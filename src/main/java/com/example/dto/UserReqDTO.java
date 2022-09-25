package com.example.dto;

import com.example.model.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserReqDTO {

    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @Min(value = 0, message = "Age should be greater than 0")
    private int age;

    @NotEmpty(message = "Email shouldn't be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Username shouldn't be empty")
    @Size(min = 3, message = "Username should be at least 3 characters")
    private String username;

    @NotEmpty(message = "Password shouldn't be empty")
    @Size(min = 3, message = "Password should be at least 3 characters")
    private String password;

    @NotEmpty
    private String[] rolesNames;
}
