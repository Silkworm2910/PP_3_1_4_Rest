package com.example.service;

import com.example.dao.RoleDAO;
import com.example.dao.UserDAO;
import com.example.dto.UserReqDTO;
import com.example.dto.UserRespDTO;
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
    public UserRespDTO findUserByID(int id) {
        UserRespDTO userRespDTO = new UserRespDTO();
        User user = userDAO.findById(id);
        userRespDTO.setId(id);
        userRespDTO.setName(user.getName());
        userRespDTO.setUsername(user.getUsername());
        userRespDTO.setEmail(user.getEmail());
        userRespDTO.setAge(user.getAge());
        String[] rolesNames = new String[user.getRoles().size()];
        int i = 0;
        for (Role role : user.getRoles()) {
            rolesNames[i++] = role.getName();
        }
        userRespDTO.setRolesNames(rolesNames);
        return userRespDTO;
    }

    @Override
    public List<UserRespDTO> findAllUsers() {
        List<UserRespDTO> userRespDTOS = new ArrayList<>();
        List<User> userList = userDAO.findAllUsers();
        for (User user : userList) {
            UserRespDTO userRespDTO = new UserRespDTO();
            userRespDTO.setId(user.getId());
            userRespDTO.setName(user.getName());
            userRespDTO.setUsername(user.getUsername());
            userRespDTO.setEmail(user.getEmail());
            userRespDTO.setAge(user.getAge());
            String[] rolesNames = new String[user.getRoles().size()];
            int i = 0;
            for (Role role : user.getRoles()) {
               rolesNames[i++] = role.getName();
            }
            userRespDTO.setRolesNames(rolesNames);
            userRespDTOS.add(userRespDTO);
        }
        return userRespDTOS;
    }

    @Override
    @Transactional
    public boolean saveUser(UserReqDTO userReqDTO) {
        User userFromDB = userDAO.findByUsername(userReqDTO.getUsername()).orElse(null);
        if (userFromDB == null) {
            User user = new User();
            Set<Role> roles = roleDAO.findAllByNameIn(userReqDTO.getRolesNames());
            user.setName(userReqDTO.getName());
            user.setUsername(userReqDTO.getUsername());
            user.setEmail(userReqDTO.getEmail());
            user.setAge(userReqDTO.getAge());
            user.setPassword(encoder.encode(userReqDTO.getPassword()));
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
    public boolean updateUserByID(int id, UserReqDTO userReqDTO) {
        User userFromDB = userDAO.findById(id);
        if (userFromDB != null) {
                Set<Role> roles = new HashSet<>(roleDAO.findAllByNameIn(userReqDTO.getRolesNames()));
                userFromDB.setRoles(roles);
            if (!"*****".equals(userReqDTO.getPassword())) {
                userFromDB.setPassword(encoder.encode(userReqDTO.getPassword()));
            }
            userFromDB.setName(userReqDTO.getName());
            userFromDB.setAge(userReqDTO.getAge());
            userFromDB.setEmail(userReqDTO.getEmail());
            userFromDB.setUsername(userReqDTO.getUsername());
            userDAO.updateUser(userFromDB);
            return true;
        }
        else {
            return false;
        }
    }
}
