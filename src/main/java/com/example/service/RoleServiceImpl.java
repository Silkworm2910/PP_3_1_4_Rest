package com.example.service;

import com.example.dao.RoleDAO;
import com.example.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@Service
@Getter
@Setter
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleDAO roleDAO;

    @Override
    @Transactional
    public void saveRole(Role role) {
        Role roleFromDB = roleDAO.findByName(role.getName()).orElse(null);
        if (roleFromDB == null) {
          roleDAO.saveRole(role);
        }
    }

    @Override
    public Set<Role> findAllRoles() {
        return roleDAO.findAllRoles();
    }

}
