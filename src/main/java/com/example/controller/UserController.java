package com.example.controller;

import com.example.model.User;
import com.example.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

        private final UserServiceImpl userService;

        @Autowired
        public UserController(UserServiceImpl userService) {
            this.userService = userService;
        }

        @GetMapping
        public String findById(@AuthenticationPrincipal User user, Model model) {
            model.addAttribute("user", userService.findUserByID(user.getId()));
            return "user-info";
        }

    }

