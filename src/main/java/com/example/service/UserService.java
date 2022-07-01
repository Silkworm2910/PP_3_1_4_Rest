package com.example.service;

import com.example.dao.RoleDAO;
import com.example.dao.UserDAO;
import com.example.model.Role;
import com.example.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;


@Service
@Getter
@Setter
@AllArgsConstructor
public class UserService implements UserDetailsService {

    @PersistenceContext
    private EntityManager entityManager;

    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User FindUserByID(int id) {
        Optional<User> userFromDB = userDAO.findById(id);
        return userFromDB.orElse(new User());
    }

    public List<User> allUsers() {
        return userDAO.findAll();
    }

    @Transactional
    public boolean saveUser(User user, String[] rolesNames) {
        User userFromDB = userDAO.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        Set<Role> roles = new HashSet<>(roleDAO.findAllByNameIn(rolesNames));
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(roles);
        userDAO.save(user);
        return true;
    }

    public boolean deleteUser(int id) {
        if (userDAO.findById(id).isPresent()) {
            userDAO.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void updateUser(User user, String[] rolesNames) {
        if (rolesNames != null) {
            Set<Role> roles = new HashSet<>(roleDAO.findAllByNameIn(rolesNames));
            user.setRoles(roles);
        }
        user.setPassword(encoder.encode(user.getPassword()));
        entityManager.merge(user);
    }

    @Transactional
    public void createInitUser() {
        User initUser = new User();
        initUser.setName("admin");
        initUser.setUsername("admin");
        initUser.setEmail("admin@mail.com");
        initUser.setPassword("admin");
        String[] roles = {"Admin", "User"};
        saveUser(initUser, roles);
    }

    @Transactional
    public void createDefaultUser() {
        User defaultUser = new User();
        defaultUser.setUsername("user" + (int)(Math.random() * 1000));
        defaultUser.setName(defaultUser.getUsername());
        defaultUser.setEmail(defaultUser.getUsername() + "@gmail.com");
        defaultUser.setPassword("12345");
        String[] roles = {"User"};
        saveUser(defaultUser, roles);
    }

    public Optional<User> findUserByRoles(Set<String> roles) {
        return userDAO.findFirstByRoles_AuthorityIn(roles);
    }
}
