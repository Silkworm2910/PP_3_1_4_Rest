package com.example.config;

import com.example.dto.UserReqDTO;
import com.example.model.Role;
import com.example.service.RoleService;
import com.example.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@AllArgsConstructor
public class DBInitializer {

    private final UserService userService;
    private final RoleService roleService;

    @PostConstruct
    private void initDatabase() {
        addAdminRole();
        addUserRole();
        addAdmin();
    }

    private void addAdminRole() {
        Role role = new Role();
        role.setAuthority("ROLE_ADMIN");
        role.setName("Admin");
        roleService.saveRole(role);
    }

    private void addUserRole() {
        Role role = new Role();
        role.setAuthority("ROLE_USER");
        role.setName("User");
        roleService.saveRole(role);
    }

    public void addAdmin() {
        UserReqDTO initUser = new UserReqDTO();
        initUser.setName("admin");
        initUser.setUsername("admin");
        initUser.setEmail("admin@mail.com");
        initUser.setPassword("admin");
        initUser.setRolesNames(new String[] {"Admin", "User"});
        userService.saveUser(initUser);
    }

}
