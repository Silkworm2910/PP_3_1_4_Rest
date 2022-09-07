package com.example.controller;

import com.example.model.Role;
import com.example.model.User;
import com.example.service.RoleServiceImpl;
import com.example.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    @GetMapping
    public String findAll(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("user", userService.findUserByID(user.getId()));
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.findAllRoles());
        return "user-list";
    }


    @PostMapping("/new")
    public String create(@Valid User newUser,
                         BindingResult bindingResult,
                         @AuthenticationPrincipal User user,
                         Model model,
                         @RequestParam(value = "rolesNames") String[] rolesNames) {
        if (bindingResult.hasErrors() || "*****".equals(newUser.getPassword())) {
            model.addAttribute("roles", roleService.findAllRoles());
            model.addAttribute("newUser", newUser);
            model.addAttribute("user", user);
            return "user-list";
        }

        if (!userService.saveUser(newUser, rolesNames)) {
            model.addAttribute("userExistsError", "User with this username already exists");
            model.addAttribute("newUser", newUser);
            model.addAttribute("roles", roleService.findAllRoles());
            return "user-list";
        }

        return "redirect:/admin";
    }

    @RequestMapping("/user-get")
    @ResponseBody
    public User getUser(Integer id) {
        return userService.findUserByID(id);
    }

    @RequestMapping("/roles-get")
    @ResponseBody
    public Set<Role> getAllRoles() {
        return roleService.findAllRoles();
    }

    @PostMapping("/edit")
    public String update(User editUser,
                         @RequestParam(value = "rolesNamesEdit") String[] rolesNamesEdit) {
        userService.updateUser(editUser, rolesNamesEdit);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String delete(User deleteUser) {
        userService.deleteUserById(deleteUser.getId());
        return "redirect:/admin";
    }
}
