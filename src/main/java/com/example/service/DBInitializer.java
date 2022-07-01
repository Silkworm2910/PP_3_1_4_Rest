package com.example.service;

import com.example.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

@Service
public class DBInitializer {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserManagementService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleManagementService(RoleService roleService) {
        this.roleService = roleService;
    }

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
        return userService.findUserByRoles(Set.of("ROLE_ADMIN")).isPresent();
    }

    private void addAdmin() {
        userService.createInitUser();
    }

}
