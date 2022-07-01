package com.example.dao;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserDAO extends JpaRepository<User,Integer> {

    User findByUsername(String username);

    Optional<User> findFirstByRoles_AuthorityIn(Set<String> roles);
}
