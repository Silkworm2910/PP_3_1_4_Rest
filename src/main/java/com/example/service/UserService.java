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
import java.util.*;


@Service
@Getter
@Setter
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public User findUserByID(int id) {
        Optional<User> userFromDB = userDAO.findById(id);
        return userFromDB.orElse(new User());
    }

    public List<User> allUsers() {
        return userDAO.findAll();
    }

    @Transactional
    public void saveUser(User user, String[] rolesNames) {
        User userFromDB = userDAO.findByUsername(user.getUsername());
        if (userFromDB == null) {
            Set<Role> roles = new HashSet<>(roleDAO.findAllByNameIn(rolesNames));
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRoles(roles);
            userDAO.save(user);
        }
    }

    public void deleteUser(int id) {
        if (userDAO.findById(id).isPresent()) {
            userDAO.deleteById(id);
        }
    }

    @Transactional
    public void updateUser(User user, String[] rolesNames) {
        if (rolesNames != null) {
            Set<Role> roles = new HashSet<>(roleDAO.findAllByNameIn(rolesNames));
            user.setRoles(roles);
        }
        if ("*****".equals(user.getPassword())) {
            User userFromDB = userDAO.findById(user.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("no user with such id"));
            user.setPassword(userFromDB.getPassword());
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
        }
        userDAO.save(user);
    }

    public Optional<User> findUserByRoles(Set<String> roles) {
        return userDAO.findFirstByRoles_AuthorityIn(roles);
    }
}
