package com.example.controller;

import com.example.dto.UserRespDTO;
import com.example.model.User;
import com.example.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

        private final UserServiceImpl userService;

        @Autowired
        public UserController(UserServiceImpl userService) {
            this.userService = userService;
        }

        @GetMapping
        public ResponseEntity<UserRespDTO> findById(@AuthenticationPrincipal User user) {
            return new ResponseEntity<>(userService.findUserByID(user.getId()), HttpStatus.OK);
        }

    }

