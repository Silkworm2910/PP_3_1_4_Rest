package com.example.service;

import com.example.dao.RoleDAO;
import com.example.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@Getter
@Setter
@AllArgsConstructor
public class RoleService {

    private final RoleDAO roleDAO;

    public boolean roleExists(String role) {
        return roleDAO.findByAuthority(role) != null;
    }

    public void saveRole(Role role) {
        Role roleFromDB = roleDAO.findByName(role.getName());
        if (roleFromDB == null) {
          roleDAO.save(role);
        }
    }

    public List<Role> findAll() {
        return roleDAO.findAll();
    }
}
