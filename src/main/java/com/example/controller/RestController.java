package com.example.controller;

import com.example.dto.UserReqDTO;
import com.example.dto.UserRespDTO;
import com.example.model.Role;
import com.example.service.RoleServiceImpl;
import com.example.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@org.springframework.web.bind.annotation.RestController
@AllArgsConstructor
@RequestMapping("/api")
public class RestController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    @GetMapping("/users")
    public ResponseEntity<List<UserRespDTO>> findAll() {
        final List<UserRespDTO> users = userService.findAllUsers();

        return users != null && !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/roles")
    public ResponseEntity<Set<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.findAllRoles(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody @Valid UserReqDTO userReqDTO) {
        userService.saveUser(userReqDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserRespDTO> findUserByID(@PathVariable(name = "id") int id) {
        final UserRespDTO userRespDTO = userService.findUserByID(id);

        return userRespDTO != null
                ? new ResponseEntity<>(userRespDTO, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id,
                                    @RequestBody @Valid UserReqDTO userReqDTO) {
        final boolean updated = userService.updateUserByID(id, userReqDTO);
        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        final boolean deleted = userService.deleteUserById(id);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
