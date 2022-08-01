package com.example.config;

import com.example.model.Role;
import com.example.model.User;
import com.example.service.RoleService;
import com.example.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
@AllArgsConstructor
public class DBInitializer {

    private final UserService userService;
    private final RoleService roleService;

    @PostConstruct
    private void initDatabase() {
        if (!adminRoleExists()) {
            addAdminRole();
        }

        if (!userRoleExists()) {
            addUserRole();
        }

        if (!adminExists()) {
            addAdmin();
        }
    }

    private boolean adminRoleExists() {
        return roleService.roleExists("ROLE_ADMIN");
    }

    private void addAdminRole() {
        Role role = new Role();
        role.setAuthority("ROLE_ADMIN");
        role.setName("Admin");
        roleService.saveRole(role);
    }

    private boolean userRoleExists() {
        return roleService.roleExists("ROLE_USER");
    }

    private void addUserRole() {
        Role role = new Role();
        role.setAuthority("ROLE_USER");
        role.setName("User");
        roleService.saveRole(role);
    }

    private boolean adminExists() {
        return userService.findUserByRoles(Set.of("Admin")).isPresent();
    }

    @Transactional
    public void addAdmin() {
        User initUser = new User();
        initUser.setName("admin");
        initUser.setUsername("admin");
        initUser.setEmail("admin@mail.com");
        initUser.setPassword("admin");
        String[] roles = {"Admin", "User"};
        userService.saveUser(initUser, roles);
    }


}
