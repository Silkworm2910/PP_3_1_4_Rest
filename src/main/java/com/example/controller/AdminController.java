package com.example.controller;

import com.example.model.Role;
import com.example.model.User;
import com.example.service.RoleServiceImpl;
import com.example.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAll(@AuthenticationPrincipal User user) {
        final List<User> users = userService.findAllUsers();

        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody User newUser,
                                    @AuthenticationPrincipal User user,
                                    @RequestParam(value = "rolesNames") String[] rolesNames) {
        userService.saveUser(newUser, rolesNames);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> findUserByID(@PathVariable(name = "id") int id) {
        final User user = userService.findUserByID(id);

        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id,
                                    @RequestBody User editUser,
                                    @RequestParam(value = "rolesNamesEdit") String[] rolesNamesEdit) {
        final boolean updated = userService.updateUserByID(editUser.getId(), rolesNamesEdit);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id,
                                    @RequestBody User deleteUser) {
        final boolean deleted = userService.deleteUserById(deleteUser.getId());
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
