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
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final BCryptPasswordEncoder encoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAO.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public User findUserByID(int id) {
        return userDAO.findById(id);
    }

    @Override
    public List<User> findAllUsers() {
        return userDAO.findAllUsers();
    }

    @Override
    @Transactional
    public boolean saveUser(User user, String[] rolesNames) {
        User userFromDB = userDAO.findByUsername(user.getUsername()).orElse(null);
        if (userFromDB == null) {
            Set<Role> roles = roleDAO.findAllByNameIn(rolesNames);
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRoles(roles);
            userDAO.saveUser(user);
            return true;
        } else {
            return false;
        }
    }


    @Override
    @Transactional
    public boolean deleteUserById(int id) {
        User userFromDB = userDAO.findById(id);
        if (userFromDB != null) {
            userDAO.deleteUserById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public boolean updateUserByID(int id, String[] rolesNames) {
        User userFromDB = userDAO.findById(id);
        if (userFromDB != null) {
            if (rolesNames != null) {
                Set<Role> roles = new HashSet<>(roleDAO.findAllByNameIn(rolesNames));
                userFromDB.setRoles(roles);
            }
            if ("*****".equals(userFromDB.getPassword())) {
                userFromDB.setPassword(userFromDB.getPassword());
            } else {
                userFromDB.setPassword(encoder.encode(userFromDB.getPassword()));
            }
            userDAO.updateUser(userFromDB);
            return true;
        }
        else {
            return false;
        }
    }
}
