package com.example.service;

import com.example.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean saveUser(User user, String[] rolesNames);

    User findUserByID(int id);

    boolean updateUserByID(int id, String[] rolesNames);

    boolean deleteUserById(int id);

    List<User> findAllUsers();
}
